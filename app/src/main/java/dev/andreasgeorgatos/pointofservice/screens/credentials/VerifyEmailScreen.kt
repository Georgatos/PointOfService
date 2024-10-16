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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.andreasgeorgatos.pointofservice.LOGIN_ROUTE
import dev.andreasgeorgatos.pointofservice.data.dto.user.VerificationDTO
import dev.andreasgeorgatos.pointofservice.screens.TextInputField
import dev.andreasgeorgatos.pointofservice.utils.FormValidator
import dev.andreasgeorgatos.pointofservice.utils.ValidationAlertDialog
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback

@Composable
fun VerifyEmailScreen(email: String, navController: NavController) {
    var verificationCode by remember { mutableStateOf("") }
    var showAlertDialog by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    fun validateForm(): Boolean {
        val fields = mapOf("Verification Code" to verificationCode)
        val errors = FormValidator.validate(fields)
        alertMessage = FormValidator.errorsToString(errors)
        return errors.isEmpty()
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
                val verificationDTO = VerificationDTO(email, verificationCode)

                RetrofitClient.userService.verifyUser(verificationDTO)
                    .enqueue(object : Callback<Void> {
                        override fun onResponse(
                            call: Call<Void>,
                            response: retrofit2.Response<Void>
                        ) {
                            navController.navigate(LOGIN_ROUTE)
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            alertMessage = "Verification failed = ${t.message}"
                            showAlertDialog = true
                        }
                    })
            } else {
                alertMessage = "Please fill the form properly"
                showAlertDialog = true
            }
        }) {
            Text(text = "Verify your account")
        }

        ValidationAlertDialog(
            showDialog = showAlertDialog,
            onDismiss = { showAlertDialog = false },
            message = alertMessage
        )
    }
}
