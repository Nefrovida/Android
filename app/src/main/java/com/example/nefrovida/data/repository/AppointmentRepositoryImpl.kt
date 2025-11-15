package com.example.nefrovida.data.repository

import com.example.nefrovida.data.remote.api.AppointmentApi
import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class AppointmentRepositoryImpl @Inject constructor (
    private val api: AppointmentApi
) : AppointmentRepository {
    override suspend fun getAppointmentList(): List<Appointment>{
        val response = api.getAppointmentList()
        return response.map { it.toDomain() }
    }
    override suspend fun getAppointmentListByDate(date: String): List<Appointment> {
        val response = api.getAppointmentListByDate(date)
        return response.map{it.toDomain()}
    }

    override suspend fun getAppointmentById(id: Int): Appointment{
        return api.getAppointmentById(id).toDomain()
    }

    override suspend fun cancelAppointmentById(id: Int) : Boolean {
        return api.cancelAppointment(id)
    }
}