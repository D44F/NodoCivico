package com.android.nodocivico.network

import com.android.nodocivico.model.Report
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("reports")
    suspend fun getAllReports(): List<Report>

    @POST("reports")
    suspend fun createReport(@Body report: Report): Response<Report>

    @PUT("reports/{id}")
    suspend fun updateReport(@Path("id") id: Int, @Body report: Report): Response<Report>
}