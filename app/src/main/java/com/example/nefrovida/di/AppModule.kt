package com.example.nefrovida.di

import com.example.nefrovida.data.remote.api.AppointmentApi
import com.example.nefrovida.data.repository.AppointmentRepositoryImpl
import com.example.nefrovida.domain.repository.AppointmentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.221:3001/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAppointmentApi(retrofit: Retrofit): AppointmentApi {
        return retrofit.create(AppointmentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppointmentRepository(
        api: AppointmentApi
    ) : AppointmentRepository {
        return AppointmentRepositoryImpl(api)
    }
}