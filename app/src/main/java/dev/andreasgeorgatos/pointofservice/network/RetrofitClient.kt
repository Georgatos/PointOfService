package dev.andreasgeorgatos.pointofservice.network

import UserService
import android.os.Build
import androidx.annotation.RequiresApi
import com.android.volley.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.andreasgeorgatos.pointofservice.network.grud.EmployeeService
import dev.andreasgeorgatos.pointofservice.network.grud.TableService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
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


    @RequiresApi(Build.VERSION_CODES.O)
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
        .create()


    @RequiresApi(Build.VERSION_CODES.O)
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(GsonConverterFactory.create()).build()

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val tableService: TableService by lazy {
        retrofit.create(TableService::class.java)
    }

    val employeeService: EmployeeService by lazy {
        retrofit.create(EmployeeService::class.java)
    }
}
