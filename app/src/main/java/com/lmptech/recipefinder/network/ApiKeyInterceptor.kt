package com.lmptech.recipefinder.network

import android.content.res.Resources
import androidx.compose.ui.platform.LocalContext
import com.lmptech.recipefinder.R
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = "f37528ed31b44ac4a8402a0dc9900100"//Resources.getSystem().getString(R.string.api_key)

        val currentUrl = chain.request().url
        val newUrl = currentUrl.newBuilder().addQueryParameter("apiKey", apiKey).build()
        val currentRequest = chain.request().newBuilder()
        val newRequest = currentRequest.url(newUrl).build()
        return chain.proceed(newRequest)
    }
}
