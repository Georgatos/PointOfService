package dev.andreasgeorgatos.pointofservice.screens.credentials.resetpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.andreasgeorgatos.pointofservice.LOGIN_ROUTE
import dev.andreasgeorgatos.pointofservice.data.dto.user.ResetPasswordDTO
import dev.andreasgeorgatos.pointofservice.screens.TextInputField
import dev.andreasgeorgatos.pointofservice.screens.credentials.FormValidator
import dev.andreasgeorgatos.pointofservice.screens.credentials.ValidationAlertDialog
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ResetPasswordScreen(navController: NavController) {

    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (confirmPassword, setConfirmPassword) = remember { mutableStateOf("") }
    val (token, setToken) = remember { mutableStateOf("") }
    var (showAlertDialog, setShowAlertDialog) = remember { mutableStateOf(false) }
    var (alertMessage, setAlertMessage) = remember { mutableStateOf("") }

    fun validateForm(): Boolean {
        val fields = mapOf(
            "Email" to email,
            "Password" to password,
            "Confirm Password" to confirmPassword,
            "Token" to token
        )

        val errors = FormValidator.validate(fields)
        setAlertMessage(FormValidator.errorsToString(errors))
        return errors.isEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            TextInputField(
                value = email,
                onValueChange = setEmail,
                label = "Email",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            TextInputField(
                value = password,
                onValueChange = setPassword,
                visualTransformation = PasswordVisualTransformation(),
                label = "Password",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))

            TextInputField(
                value = confirmPassword,
                onValueChange = setConfirmPassword,
                visualTransformation = PasswordVisualTransformation(),
                label = "Confirm Password",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            TextInputField(
                value = token,
                onValueChange = setToken,
                label = "Token",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                if (validateForm()) {
                    val resetPasswordDTO = ResetPasswordDTO(email, password, confirmPassword, token)

                    RetrofitClient.userService.resetPassword(resetPasswordDTO)
                        .enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.isSuccessful) {
                                    navController.navigate(LOGIN_ROUTE)
                                } else {
                                    alertMessage = "registration failed"
                                    showAlertDialog = true
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                alertMessage = "registration failed"
                                showAlertDialog = true
                            }
                        })
                } else {
                    setShowAlertDialog(true)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text("Reset Password")
        }
    }

    ValidationAlertDialog(
        showDialog = showAlertDialog,
        onDismiss = { setShowAlertDialog(false) },
        message = alertMessage
    )
}
