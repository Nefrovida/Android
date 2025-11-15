package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.AppointmentListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AppointmentApi {
    @GET("secretary-agenda")
    suspend fun getAppointmentList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ) : List<AppointmentListDto>
    @GET("appointments-per-day")
    suspend fun getAppointmentListByDate(
        @Query("date") date:String
    ): AppointmentListDto

}