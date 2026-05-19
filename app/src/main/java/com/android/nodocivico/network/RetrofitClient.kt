package com.android.nodocivico.network

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var apiService: ApiService? = null
    private var currentIp: String? = null

    fun getInstance(context: Context): ApiService {
        val prefs = context.getSharedPreferences("nodo_prefs", Context.MODE_PRIVATE)
        val ip = prefs.getString("server_ip", "10.0.2.2") ?: "10.0.2.2"

        if (apiService == null || currentIp != ip) {
            currentIp = ip
            val baseUrl = "http://$ip:5000/"
            
            apiService = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
        return apiService!!
    }

    fun reset() {
        apiService = null
    }
}