package dev.andreasgeorgatos.pointofservice.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.atomic.AtomicReference

class HeaderInterceptor : Interceptor {

    companion object {
        private val authorizationInfo = AtomicReference<String?>(null)
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var url = request.url.toString()

        if (url.contains("/register") || url.contains("/login") || url.contains("/forgotPassword") || url.contains("/resetPassword")) {
            val response = chain.proceed(request)
            val headers = response.headers

            if (headers["Authorization"] != null) {
                var authInfo: String = headers["Authorization"]?.removePrefix("Bearer ") ?: ""
                authorizationInfo.set(authInfo)
            }
            return response
        } else {
            val newRequest = request.newBuilder()
                .apply {
                    authorizationInfo.get()?.let {
                        addHeader("Authorization", "Bearer $it")
                    }
                }
                .build()
            return chain.proceed(newRequest)
        }
    }
}
