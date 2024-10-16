package dev.andreasgeorgatos.pointofservice.screens.management.tables.add

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import dev.andreasgeorgatos.pointofservice.MANAGER_MAIN_SCREEN
import dev.andreasgeorgatos.pointofservice.data.dto.tables.TableDTO
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import dev.andreasgeorgatos.pointofservice.screens.IntegerInputField
import dev.andreasgeorgatos.pointofservice.utils.FormValidator
import dev.andreasgeorgatos.pointofservice.utils.ValidationAlertDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddSingleTableScreen(navController: NavController) {

    var tableNumber by remember { mutableStateOf("") }
    var alertMessage by remember { mutableStateOf("") }
    var showAlertDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Add Single Table", fontSize = 32.sp
        )
        IntegerInputField(
            value = tableNumber, onValueChange = { tableNumber = it }, label = "Table Number"
        )

        fun validateForm(): Boolean {
            val fields = mapOf(
                "Digit" to tableNumber
            )

            val errors = FormValidator.validate(fields)
            if (errors.isNotEmpty()) {
                alertMessage = FormValidator.errorsToString(errors)
                return false
            }

            return true
        }
        ValidationAlertDialog(
            showDialog = showAlertDialog,
            onDismiss = { showAlertDialog = false },
            message = alertMessage
        )
        Button(onClick = {
            if (validateForm()) {

                val gson = GsonBuilder().registerTypeAdapter(LocalDate::class.java,
                    JsonSerializer<LocalDate> { src, _, _ ->
                        JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE))
                    })
                    .registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, _, _ ->
                        LocalDate.parse(json.asString, DateTimeFormatter.ISO_LOCAL_DATE)
                    }).create()

                val json = gson.toJson(TableDTO(tableNumber.toInt()))
                val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

                RetrofitClient.tableService.getTableByTableNumber(tableNumber.toLong())
                    .enqueue(object : Callback<TableDTO> {
                        override fun onResponse(
                            call: Call<TableDTO>, response: Response<TableDTO>
                        ) {
                            if (response.isSuccessful) {
                                alertMessage = "Table already exists."
                                showAlertDialog = true
                            } else {
                                RetrofitClient.tableService.createTable(requestBody)
                                    .enqueue(object : Callback<Void> {
                                        override fun onResponse(
                                            call: Call<Void>, response: Response<Void>
                                        ) {
                                            if (response.isSuccessful) {
                                                alertMessage = "Table has been created."
                                                showAlertDialog = true
                                                navController.navigate(MANAGER_MAIN_SCREEN)
                                            } else {
                                                val errorResponse = response.errorBody()?.string()
                                                    ?: "Unknown error"
                                                alertMessage = errorResponse
                                                showAlertDialog = true
                                            }
                                        }

                                        override fun onFailure(call: Call<Void>, t: Throwable) {
                                            alertMessage = "Registration failed: ${t.message}"
                                            showAlertDialog = true
                                        }
                                    })
                            }
                        }

                        override fun onFailure(call: Call<TableDTO>, t: Throwable) {
                            alertMessage = "Failed to enquire the database. ${t.cause}"
                            showAlertDialog = true
                        }

                    })
            } else {
                showAlertDialog = true
            }
        }) {
            Text(
                text = "Add a single table", fontSize = 20.sp
            )
        }
    }
}
