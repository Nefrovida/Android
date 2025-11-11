package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.AppointmentListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SecretaryApi {
    @GET("appointment")
    suspend fun getAppointmentList(
        @Path("date") date:String
    ): AppointmentListDto
}