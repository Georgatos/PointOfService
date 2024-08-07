package dev.andreasgeorgatos.pointofservice.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val responseBody = response.body
        val responseBodyString = responseBody?.string()
        Log.e("API Response", "Error response: $responseBodyString")

        // To not consume the response body, we need to create a new response body and replace it
        val newResponseBody =
            ResponseBody.create(responseBody?.contentType(), responseBodyString ?: "")
        return response.newBuilder().body(newResponseBody).build()
    }
}