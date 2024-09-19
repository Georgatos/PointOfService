package dev.andreasgeorgatos.pointofservice.screens.management.shift

import android.util.Log
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
import dev.andreasgeorgatos.pointofservice.data.dto.user.EmployeeDTO
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import dev.andreasgeorgatos.pointofservice.screens.IntegerInputField
import dev.andreasgeorgatos.pointofservice.screens.credentials.FormValidator
import dev.andreasgeorgatos.pointofservice.screens.credentials.ValidationAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun StartShiftScreen(navController: NavController) {

    var shiftIdNumber by remember { mutableStateOf("") }
    var alertMessage by remember { mutableStateOf("") }
    var showAlertDialog by remember { mutableStateOf(false) }
    var employees by remember { mutableStateOf(mapOf<Long, EmployeeDTO>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Start shift", fontSize = 32.sp
        )
        IntegerInputField(
            value = shiftIdNumber,
            onValueChange = { shiftIdNumber = it },
            label = "Employee ID number"
        )

        fun validateForm(): Boolean {
            val fields = mapOf(
                "Digit" to shiftIdNumber
            )

            val errors = FormValidator.validate(fields)
            if (errors.isNotEmpty()) {
                alertMessage = FormValidator.errorsToString(errors)
                return false
            }

            return true
        }

        RetrofitClient.userService.getAllEmployees()
            .enqueue(object : Callback<Map<Long, EmployeeDTO>> {
                override fun onResponse(
                    call: Call<Map<Long, EmployeeDTO>>, response: Response<Map<Long, EmployeeDTO>>
                ) {
                    if (response.isSuccessful) {
                        if (response.isSuccessful) {
                            employees = response.body() ?: mapOf()
                        }
                    }
                }

                override fun onFailure(call: Call<Map<Long, EmployeeDTO>>, t: Throwable) {
                    Log.d("on failure shift screen", t.message.toString())
                }
            })

        ValidationAlertDialog(
            showDialog = showAlertDialog,
            onDismiss = { showAlertDialog = false },
            message = alertMessage
        )
        Button(onClick = {}) {
            Text(
                text = "Start shift", fontSize = 20.sp
            )
        }
    }
}
