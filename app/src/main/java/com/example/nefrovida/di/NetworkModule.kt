package com.example.nefrovida.di

import android.content.Context
import com.example.nefrovida.data.remote.api.AnalysisHistoryApi
import com.example.nefrovida.data.remote.api.AppointmentApi
import com.example.nefrovida.data.remote.api.AuthApiService
import com.example.nefrovida.data.remote.api.CatalogApi
import com.example.nefrovida.data.remote.api.ForumApiService
import com.example.nefrovida.data.remote.api.ProfileApi
import com.example.nefrovida.data.remote.api.RefreshAuthenticator
import com.example.nefrovida.data.remote.api.ReportsApi
import com.example.nefrovida.data.repository.AnalysisHistoryRepositoryImpl
import com.example.nefrovida.data.repository.AppointmentRepositoryImpl
import com.example.nefrovida.data.repository.ForumRepositoryImpl
import com.example.nefrovida.data.repository.ProfileRepositoryImpl
import com.example.nefrovida.domain.repository.AnalysisHistoryRepository
import com.example.nefrovida.domain.repository.AppointmentRepository
import com.example.nefrovida.domain.repository.ForumRepository
import com.example.nefrovida.domain.repository.ProfileRepository
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://192.168.100.9:3001/api/" // Android emulator localhost

    // For physical device, use your computer's IP: "http://192.168.x.x:3001/api/"
    private var retrofit: Retrofit? = null
    private var cookieJar: PersistentCookieJar? = null

    @Provides
    @Singleton
    fun provideRetrofit(
        @ApplicationContext context: Context,
    ): Retrofit {
        if (retrofit == null) {
            retrofit = createRetrofit(context)
        }
        return retrofit!!
    }

    @Provides
    @Singleton
    fun provideAppointmentApi(retrofit: Retrofit): AppointmentApi = retrofit.create(AppointmentApi::class.java)

    @Provides
    @Singleton
    fun provideReportsApi(retrofit: Retrofit): ReportsApi = retrofit.create(ReportsApi::class.java)

    @Provides
    @Singleton
    fun provideForumApi(retrofit: Retrofit): ForumApiService = retrofit.create(ForumApiService::class.java)

    @Provides
    @Singleton
    fun provideAppointmentRepository(api: AppointmentApi): AppointmentRepository = AppointmentRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideAnalysisHistoryApi(retrofit: Retrofit): AnalysisHistoryApi = retrofit.create(AnalysisHistoryApi::class.java)

    @Provides
    @Singleton
    fun provideAnalysisHistoryRepository(api: AnalysisHistoryApi): AnalysisHistoryRepository = AnalysisHistoryRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi): ProfileRepository = ProfileRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideForumRepository(api: ForumApiService): ForumRepository = ForumRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideCatalogApi(retrofit: Retrofit): CatalogApi = retrofit.create(CatalogApi::class.java)

    private fun createRetrofit(context: Context): Retrofit {
        // Create persistent cookie jar
        cookieJar =
            PersistentCookieJar(
                SetCookieCache(),
                SharedPrefsCookiePersistor(context.applicationContext),
            )

        // Create refresh client without authenticator to avoid infinite loops
        val refreshClient =
            OkHttpClient
                .Builder()
                .cookieJar(cookieJar!!)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        // Create authenticator with refresh client
        val authenticator =
            RefreshAuthenticator(
                refreshClient = refreshClient,
                refreshUrl = BASE_URL + "auth/refresh",
            )

        // Create logging interceptor for debugging
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        // Create cookie interceptor that forces cookies for all paths
        val cookieForcingInterceptor =
            okhttp3.Interceptor { chain ->
                val originalRequest = chain.request()
                val url = originalRequest.url

                android.util.Log.d("NetworkModule", "========== REQUEST ==========")
                android.util.Log.d("NetworkModule", "URL: $url")

                // Get all cookies from the cookie jar
                val allCookies = cookieJar!!.loadForRequest(url)
                android.util.Log.d("NetworkModule", "Cookies from CookieJar for this URL: ${allCookies.size}")

                // If no cookies for this URL, try to get cookies for the base API URL
                val cookiesToUse =
                    if (allCookies.isEmpty()) {
                        android.util.Log.d("NetworkModule", "No cookies for current URL, trying to get cookies from base API URL")
                        val baseApiUrl =
                            okhttp3.HttpUrl
                                .Builder()
                                .scheme(url.scheme)
                                .host(url.host)
                                .port(url.port)
                                .addPathSegment("api")
                                .build()

                        val apiCookies = cookieJar!!.loadForRequest(baseApiUrl)
                        android.util.Log.d("NetworkModule", "Cookies from base API URL: ${apiCookies.size}")
                        apiCookies
                    } else {
                        allCookies
                    }

                android.util.Log.d("NetworkModule", "Total cookies to use: ${cookiesToUse.size}")
                cookiesToUse.forEach { cookie ->
                    android.util.Log.d("NetworkModule", "Cookie: ${cookie.name} = ${cookie.value}")
                }

                // Build the request with cookies manually added
                val requestBuilder = originalRequest.newBuilder()

                if (cookiesToUse.isNotEmpty()) {
                    val cookieHeader = cookiesToUse.joinToString("; ") { "${it.name}=${it.value}" }
                    android.util.Log.d("NetworkModule", "Adding Cookie header: $cookieHeader")
                    requestBuilder.header("Cookie", cookieHeader)
                }

                val newRequest = requestBuilder.build()
                val response = chain.proceed(newRequest)

                android.util.Log.d("NetworkModule", "Response code: ${response.code}")
                android.util.Log.d("NetworkModule", "========== END REQUEST ==========")

                response
            }

        // Create main OkHttp client with cookie jar and authenticator
        val okHttpClient =
            OkHttpClient
                .Builder()
                .cookieJar(cookieJar!!)
                .authenticator(authenticator)
                .addInterceptor(cookieForcingInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService = retrofit.create(AuthApiService::class.java)

    fun clearCookies() {
        cookieJar?.clear()
    }

    fun getBaseUrl(): String = BASE_URL

    fun debugPrintAllCookies() {
        android.util.Log.d("NetworkModule", "========== ALL COOKIES IN JAR ==========")
        // Try to get cookies for different URLs
        val urls =
            listOf(
                "http://10.25.102.123:3001/api/",
                "http://10.25.102.123:3001/",
                "http://10.25.102.123:3001/uploads/",
                "http://10.25.102.123:3001/api/auth/login",
            )

        urls.forEach { urlString ->
            try {
                val url = urlString.toHttpUrlOrNull()
                if (url != null) {
                    val cookies = cookieJar?.loadForRequest(url)
                    android.util.Log.d("NetworkModule", "URL: $urlString - Cookies: ${cookies?.size ?: 0}")
                    cookies?.forEach { cookie ->
                        android.util.Log.d(
                            "NetworkModule",
                            "  - ${cookie.name} = ${cookie.value} (path: ${cookie.path}, domain: ${cookie.domain})",
                        )
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("NetworkModule", "Error checking cookies for $urlString", e)
            }
        }
        android.util.Log.d("NetworkModule", "========================================")
    }
}
