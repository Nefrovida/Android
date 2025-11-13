package com.example.nefrovida.data.di

import com.example.nefrovida.data.remote.api.LaboratoryApi
import com.example.nefrovida.data.repository.LabAnalysisRepositoryImpl
import com.example.nefrovida.domain.repository.LabAnalysisRepository
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
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.69:3001/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLabAnalysisApi(retrofit: Retrofit): LaboratoryApi {
        return retrofit.create(LaboratoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLabAnalysisRepository(
        api: LaboratoryApi
    ): LabAnalysisRepository {
        return LabAnalysisRepositoryImpl(api)
    }
}