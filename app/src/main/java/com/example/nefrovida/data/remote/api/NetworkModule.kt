package com.example.nefrovida.data.remote.api

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    private const val BASE_URL = "http://192.168.1.69:3001/api/" // Android emulator localhost

    // For physical device, use your computer's IP: "http://192.168.x.x:3001/api/"
    private var retrofit: Retrofit? = null
    private var cookieJar: PersistentCookieJar? = null

    fun provideRetrofit(context: Context): Retrofit {
        if (retrofit == null) {
            retrofit = createRetrofit(context)
        }
        return retrofit!!
    }

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
