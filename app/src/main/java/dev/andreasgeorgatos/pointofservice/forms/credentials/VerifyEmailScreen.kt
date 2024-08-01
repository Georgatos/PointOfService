package dev.andreasgeorgatos.pointofservice.forms.credentials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.andreasgeorgatos.pointofservice.LOGIN_ROUTE
import dev.andreasgeorgatos.pointofservice.data.dto.VerificationDTO
import dev.andreasgeorgatos.pointofservice.data.responses.VerifyAccountResponse
import dev.andreasgeorgatos.pointofservice.forms.TextInputField
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import dev.andreasgeorgatos.pointofservice.utils.Validator
import retrofit2.Call
import retrofit2.Callback

@Composable
fun VerifyEmailScreen(email: String, navController: NavController) {

    var verificationCode by remember { mutableStateOf("") }
    var showAlertDialog by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    fun validateForm(): Boolean {
        val messages = mutableListOf<String>()
        var isValid = true

        if (!Validator.isUUIDValid(verificationCode)) {
            messages.add("The verification code isn't correct")
            isValid = false
        }
        alertMessage = messages.joinToString("\n")
        return isValid
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Verify your Email")

        Spacer(modifier = Modifier.height(16.dp))

        TextInputField(
            value = verificationCode,
            onValueChange = { verificationCode = it },
            label = "Verification code",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (validateForm()) {
                val apiService = RetrofitClient.userService

                val verificationDTO: VerificationDTO = VerificationDTO(email, verificationCode)

                val call: Call<VerifyAccountResponse> = apiService.verifyUser(verificationDTO)

                call.enqueue(object : Callback<VerifyAccountResponse> {
                    override fun onResponse(
                        call: Call<VerifyAccountResponse>,
                        response: retrofit2.Response<VerifyAccountResponse>
                    ) {
                        navController.navigate(LOGIN_ROUTE)
                    }

                    override fun onFailure(call: Call<VerifyAccountResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })

            } else {
                showAlertDialog = true
            }
        }) {
            Text(text = "Verify your account")
        }

        if (showAlertDialog) {
            AlertDialog(onDismissRequest = { showAlertDialog = false }, confirmButton = {
                TextButton(onClick = { showAlertDialog = false }) {
                    Text("OK")
                }
            }, title = { Text("Validation Errors") }, text = { Text(alertMessage) })
        }

    }
}