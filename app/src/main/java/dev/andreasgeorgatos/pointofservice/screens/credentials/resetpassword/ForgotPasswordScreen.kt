package dev.andreasgeorgatos.pointofservice.screens.credentials.resetpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.andreasgeorgatos.pointofservice.RESET_PASSWORD_ROUTE
import dev.andreasgeorgatos.pointofservice.data.dto.user.EmailDTO
import dev.andreasgeorgatos.pointofservice.screens.TextInputField
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import dev.andreasgeorgatos.pointofservice.utils.ValidationAlertDialog
import dev.andreasgeorgatos.pointofservice.utils.FormValidator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ForgotPasswordScreen(navController: NavController) {

    val (email, setEmail) = remember { mutableStateOf("") }
    var (showAlertDialog, setShowAlertDialog) = remember { mutableStateOf(false) }
    var (alertMessage, setAlertMessage) = remember { mutableStateOf("") }

    fun validateForm(): Boolean {
        val fields = mapOf("E-mail" to email)
        val errors = FormValidator.validate(fields)
        setAlertMessage(FormValidator.errorsToString(errors))
        return errors.isEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Forgot Password Screen")

        Spacer(modifier = Modifier.height(16.dp))

        TextInputField(
            value = email,
            onValueChange = setEmail,
            label = "Email",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (validateForm()) {
                RetrofitClient.userService.forgotPassword(EmailDTO(email))
                    .enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                navController.navigate(RESET_PASSWORD_ROUTE)
                            } else {
                                alertMessage = "reset password failed"
                                showAlertDialog=true
                            }

                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            alertMessage = "reset password failed"
                            showAlertDialog=true
                        }
                    })
            } else {
                setShowAlertDialog(true)
            }
        }) {
            Text(text = "Forgot Password")
        }
    }

    ValidationAlertDialog(
        showDialog = showAlertDialog,
        onDismiss = { setShowAlertDialog(false) },
        message = alertMessage
    )
}
