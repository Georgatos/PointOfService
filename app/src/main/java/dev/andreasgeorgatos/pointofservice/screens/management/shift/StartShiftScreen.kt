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
import dev.andreasgeorgatos.pointofservice.data.dto.employee.EmployeeDTO
import dev.andreasgeorgatos.pointofservice.data.dto.user.UserDTO
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import dev.andreasgeorgatos.pointofservice.screens.IntegerInputField
import dev.andreasgeorgatos.pointofservice.utils.FormValidator
import dev.andreasgeorgatos.pointofservice.utils.ValidationAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun StartShiftScreen(navController: NavController) {

    var employeeID by remember { mutableStateOf("") }
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
            value = employeeID,
            onValueChange = { employeeID = it },
            label = "Employee ID number"
        )

        fun validateForm(): Boolean {
            val fields = mapOf(
                "Digit" to employeeID
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
                Log.d("employee id: ", employeeID)

                RetrofitClient.employeeService.getEmployeeById(employeeID.toLong())
                    .enqueue(object : Callback<UserDTO> {
                        override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                            if (response.isSuccessful) {
                                Log.d("on success shift screen, user: ", response.body().toString())
                            } else {
                                Log.e("onResponse failure shift screen,", response.message())
                            }
                        }
                        override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                            Log.e("onFailure, error: ", t.message.toString())
                        }
                    })
            }
        }) {
            Text(
                text = "Start shift", fontSize = 20.sp
            )
        }
    }
}