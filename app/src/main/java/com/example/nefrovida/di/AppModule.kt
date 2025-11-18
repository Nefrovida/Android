package com.example.nefrovida.di

import com.example.nefrovida.data.remote.api.AnalysisHistoryApi
import com.example.nefrovida.data.remote.api.AppointmentApi
import com.example.nefrovida.data.repository.AnalysisHistoryRepositoryImpl
import com.example.nefrovida.data.repository.AppointmentRepositoryImpl
import com.example.nefrovida.domain.repository.AnalysisHistoryRepository
import com.example.nefrovida.domain.repository.AppointmentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("http://10.0.0.2:3001/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAppointmentApi(retrofit: Retrofit): AppointmentApi = retrofit.create(AppointmentApi::class.java)

    @Provides
    @Singleton
    fun provideAppointmentRepository(api: AppointmentApi): AppointmentRepository = AppointmentRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideAnalysisHistoryApi(retrofit: Retrofit): AnalysisHistoryApi = retrofit.create(AnalysisHistoryApi::class.java)

    @Provides
    @Singleton
    fun provideAnalysisHistoryRepository(api: AnalysisHistoryApi): AnalysisHistoryRepository = AnalysisHistoryRepositoryImpl(api)
}
