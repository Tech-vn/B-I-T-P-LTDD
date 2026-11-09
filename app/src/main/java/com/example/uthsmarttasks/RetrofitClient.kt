package com.example.uthsmarttasks

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Đổi Base URL sang Firebase Realtime Database
    private const val BASE_URL = "https://uthsmarttasks-9d603-default-rtdb.firebaseio.com/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}