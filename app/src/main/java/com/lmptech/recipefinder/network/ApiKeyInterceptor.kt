package com.lmptech.recipefinder.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = "f37528ed31b44ac4a8402a0dc9900100" // change this to your actual API key

        val currentUrl = chain.request().url
        val newUrl = currentUrl.newBuilder().addQueryParameter("apiKey", apiKey).build()
        val currentRequest = chain.request().newBuilder()
        val newRequest = currentRequest.url(newUrl).build()
        return chain.proceed(newRequest)
    }
}
