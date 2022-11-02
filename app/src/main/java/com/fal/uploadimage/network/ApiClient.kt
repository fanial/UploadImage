package com.fal.uploadimage.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL = "https://market-final-project.herokuapp.com/"

    val instance : ApiService by lazy {
        val service = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        service.create(ApiService::class.java)
    }
}