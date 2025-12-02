package com.example.nefrovida.di

import android.content.Context
import com.example.nefrovida.data.remote.api.AnalysisHistoryApi
import com.example.nefrovida.data.remote.api.AppointmentApi
import com.example.nefrovida.data.remote.api.AuthApiService
import com.example.nefrovida.data.remote.api.CatalogApi
import com.example.nefrovida.data.remote.api.ForumApiService
import com.example.nefrovida.data.remote.api.RefreshAuthenticator
import com.example.nefrovida.data.remote.api.ReportsApi
import com.example.nefrovida.data.repository.AnalysisHistoryRepositoryImpl
import com.example.nefrovida.data.repository.AppointmentRepositoryImpl
import com.example.nefrovida.data.repository.ForumRepositoryImpl
import com.example.nefrovida.domain.repository.AnalysisHistoryRepository
import com.example.nefrovida.domain.repository.AppointmentRepository
import com.example.nefrovida.domain.repository.ForumRepository
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://10.25.87.229:3001/api/" // Android emulator localhost

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

        // Create main OkHttp client with cookie jar and authenticator
        val okHttpClient =
            OkHttpClient
                .Builder()
                .cookieJar(cookieJar!!)
                .authenticator(authenticator)
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

    fun provideAuthApiService(context: Context): AuthApiService = provideRetrofit(context).create(AuthApiService::class.java)

    fun clearCookies() {
        cookieJar?.clear()
    }
}
