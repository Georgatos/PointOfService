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
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import dev.andreasgeorgatos.pointofservice.data.dto.tables.TableDTO
import dev.andreasgeorgatos.pointofservice.data.dto.tables.TableNumberDTO
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import dev.andreasgeorgatos.pointofservice.screens.IntegerWithCommaInputField
import dev.andreasgeorgatos.pointofservice.screens.credentials.FormValidator
import dev.andreasgeorgatos.pointofservice.screens.credentials.ValidationAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RemoveMultipleTables(navController: NavController) {

    var tableNumbers by remember { mutableStateOf("") }
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
            text = "Add Multipe tables", fontSize = 32.sp
        )

        IntegerWithCommaInputField(
            value = tableNumbers,
            onValueChange = { tableNumbers = it },
            label = "Table Numbers (comma-separated)"
        )

        fun validateForm(): Boolean {
            val fields = mapOf(
                "Digit Or Comma" to tableNumbers
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
                val gson = GsonBuilder().registerTypeAdapter(
                    LocalDate::class.java,
                    JsonSerializer<LocalDate> { src, _, _ ->
                        JsonPrimitive(
                            src.format(DateTimeFormatter.ISO_LOCAL_DATE)
                        )
                    }).create()

                val tableNumbersList = tableNumbers.split(",").map { it.trim().toInt() }

                for (tableNumber in tableNumbersList) {
                    RetrofitClient.tableService.deleteTableByTableNumber(TableNumberDTO(tableNumber.toLong()))
                        .enqueue(
                            object : Callback<TableDTO> {

                                override fun onResponse(
                                    call: Call<TableDTO>,
                                    response: Response<TableDTO>
                                ) {
                                    if (response.isSuccessful) {
                                        alertMessage = "Successfully deleted the table."
                                        showAlertDialog = true
                                    } else {
                                        val errorResponse =
                                            response.errorBody()?.string() ?: "Unknown error"
                                        alertMessage = errorResponse
                                        showAlertDialog = true
                                    }
                                }

                                override fun onFailure(call: Call<TableDTO>, t: Throwable) {
                                    val errorResponse = t.message ?: "Unknown error"
                                    alertMessage = errorResponse
                                    showAlertDialog = true
                                }
                            })
                }
            } else {
                showAlertDialog = true
            }
        }) {
            Text(
                text = "Remove Tables", fontSize = 20.sp
            )
        }
    }
}
