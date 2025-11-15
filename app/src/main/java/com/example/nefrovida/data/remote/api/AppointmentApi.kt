package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.AppointmentDto
import com.example.nefrovida.data.remote.dto.AppointmentListDto
import com.example.nefrovida.domain.model.Appointment
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppointmentApi {
    @GET("secretary-agenda")
    suspend fun getAppointmentList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ) : List<AppointmentDto>
    @GET("appointments-per-day")
    suspend fun getAppointmentListByDate(
        @Query("date") date:String
    ): List<AppointmentDto>

    //TODO: implementar en back
    @GET("appointment/{id}")
    suspend fun getAppointmentById(@Path("id") id: Int): AppointmentDto
    @POST("appointments/{id}/cancel")
    suspend fun cancelAppointment(@Path("id") id:Int): Boolean

}