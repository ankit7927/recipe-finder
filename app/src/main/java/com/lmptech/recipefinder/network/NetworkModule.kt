package com.lmptech.recipefinder.network

import com.lmptech.recipefinder.network.services.RecipeApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkModule private constructor() {

    companion object {
        private const val BASE_URL = "https://api.spoonacular.com/"
        @Volatile
        private var instance: Retrofit? = null

        private val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()

        private fun createRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
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