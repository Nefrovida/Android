package com.example.nefrovida.di

import com.example.nefrovida.data.repository.AppointmentNotesRepositoryImpl
import com.example.nefrovida.data.repository.AppointmentRepositoryImpl
import com.example.nefrovida.data.repository.CatalogRepositoryImpl
import com.example.nefrovida.data.repository.ReportRepositoryImpl
import com.example.nefrovida.domain.repository.AppointmentNotesRepository
import com.example.nefrovida.domain.repository.AppointmentRepository
import com.example.nefrovida.domain.repository.AuthRepository
import com.example.nefrovida.domain.repository.CatalogRepository
import com.example.nefrovida.domain.repository.ReportRepository
import com.example.nefrovida.domain.usecase.ForgotPasswordUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindReportRepository(impl: ReportRepositoryImpl): ReportRepository

    @Binds
    @Singleton
    abstract fun bindAnalysisHistoryRepository(impl: AppointmentNotesRepositoryImpl): AppointmentNotesRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: com.example.nefrovida.data.repository.AuthRepositoryImpl): com.example.nefrovida.domain.repository.AuthRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CatalogRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCatalogRepository(impl: CatalogRepositoryImpl): CatalogRepository
}

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideForgotPasswordUseCase(authRepository: AuthRepository): ForgotPasswordUseCase {
        return ForgotPasswordUseCase(authRepository)
    }
}
