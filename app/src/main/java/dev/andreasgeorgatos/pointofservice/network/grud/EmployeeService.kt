package dev.andreasgeorgatos.pointofservice.network.grud

import dev.andreasgeorgatos.pointofservice.data.dto.employee.EmployeeDTO
import dev.andreasgeorgatos.pointofservice.data.dto.user.UserDTO
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EmployeeService {

    @GET("employees-controller")
    fun getAllEmployees(): Call<Map<Long, EmployeeDTO>>

    @GET("employees-controller/{id}")
    fun getEmployeeById(@Path("id") id: Long): Call<UserDTO>

    @POST("employees-controller")
    fun createShift(@Body requestBody: RequestBody): Call<Void>
}