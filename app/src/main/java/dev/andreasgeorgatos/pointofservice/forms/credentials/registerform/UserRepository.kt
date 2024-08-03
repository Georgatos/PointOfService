package dev.andreasgeorgatos.pointofservice.forms.credentials.registerform

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import dev.andreasgeorgatos.pointofservice.data.dto.UserDTO
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object UserRepository {
    @RequiresApi(Build.VERSION_CODES.O)

    fun registerUser(user: UserDTO): Call<Void> {
        val gson = GsonBuilder().registerTypeAdapter(
            LocalDate::class.java,
            JsonSerializer<LocalDate> { src, _, _ ->
                JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE))
            }).create()

        val userJson = gson.toJson(user)
        val requestBody = userJson.toRequestBody("application/json".toMediaTypeOrNull())
        return RetrofitClient.userService.registerUser(requestBody)
    }
}
