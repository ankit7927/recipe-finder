package com.lmptech.recipefinder.network

import com.lmptech.recipefinder.network.services.RecipeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkModule private constructor() {

    companion object {
        private const val BASE_URL = "http://192.168.236.24:4000/"
        @Volatile
        private var instance: Retrofit? = null

        private fun createRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getInstance(): Retrofit {
            return instance ?: synchronized(this) {
                instance ?: createRetrofit().also { instance = it }
            }
        }
    }



    fun provideApiService(retrofit: Retrofit): RecipeApiService {
        return retrofit.create(RecipeApiService::class.java)
    }
}