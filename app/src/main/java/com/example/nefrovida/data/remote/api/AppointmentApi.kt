package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.AppointmentDto
import com.example.nefrovida.data.remote.dto.AppointmentListDto
import com.example.nefrovida.data.remote.dto.AppointmentNotesDto
import com.example.nefrovida.data.remote.dto.RescheduleAppointmentDto
import com.example.nefrovida.domain.model.Appointment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AppointmentApi {
    @GET("appointments/patient/get-notes")
    suspend fun getNotes(): List<AppointmentNotesDto>

    @GET("secretary-agenda")
    suspend fun getAppointmentList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): List<AppointmentDto>

    @GET("agenda/appointments-per-day")
    suspend fun getAppointmentListByDate(
        @Query("date") date: String,
    ): List<AppointmentDto>

    @GET("agenda/appointment/{id}")
    suspend fun getAppointmentById(
        @Path("id") id: Int,
    ): AppointmentDto

    @POST("agenda/appointments/{id}/cancel")
    suspend fun cancelAppointment(
        @Path("id") id: Int,
    ): Response<Unit>

    @GET("agenda/appointments/date-availability")
    suspend fun getDateAvailability(
        @Query("appointmentName") appointmentName: String,
        @Query("date") date: String,
    ): Response<List<String>>

    @PUT("appointments/{id}/reschedule")
    suspend fun rescheduleAppointment(
        @Path("id") id: Int,
        @Body body: RescheduleAppointmentDto,
    ): Response<Unit>
}
