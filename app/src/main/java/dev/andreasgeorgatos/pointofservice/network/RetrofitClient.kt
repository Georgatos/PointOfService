package dev.andreasgeorgatos.pointofservice.network

import UserService
import com.android.volley.BuildConfig
import dev.andreasgeorgatos.pointofservice.network.grud.TableService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8080/api/v1/"

    private val okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
        .addNetworkInterceptor(HeaderInterceptor())
        .addNetworkInterceptor(LoggingInterceptor())
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }).build()


    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val tableService: TableService by lazy {
        retrofit.create(TableService::class.java)
    }
}
