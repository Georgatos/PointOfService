package dev.andreasgeorgatos.pointofservice.forms.credentials.resetpassword

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import dev.andreasgeorgatos.pointofservice.RESET_PASSWORD
import dev.andreasgeorgatos.pointofservice.data.dto.EmailDTO
import dev.andreasgeorgatos.pointofservice.forms.TextInputField
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ForgotPasswordScreen(navController: NavController) {

    val (email, setEmail) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Forgot Password Screen")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextInputField(
                value = email,
                onValueChange = setEmail,
                label = "Email",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            RetrofitClient.userService.forgotPassword(EmailDTO(email))
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Log.d("ForgotPasswordScreen", "Password reset email sent successfully")
                            navController.navigate(RESET_PASSWORD)
                        } else {
                            Log.d("ForgotPasswordScreen", "Password reset email failed to sent")
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("ForgotPasswordScreen", "error ${t.message}")
                    }
                })
        }) {
            Text(text = "Forgot Password")
        }
    }
}