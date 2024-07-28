package dev.andreasgeorgatos.pointofservice.loginform

import VolleySingleton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import dev.andreasgeorgatos.pointofservice.LOGIN_ROUTE
import dev.andreasgeorgatos.pointofservice.VERIFY_EMAIL_ROUTE
import dev.andreasgeorgatos.pointofservice.utils.Validator
import org.json.JSONObject

@Composable
fun VerifyEmailScreen(email: String, navController: NavController) {

    var UUID by remember { mutableStateOf("") }
    var showAlertDialog by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    fun validateForm(): Boolean {
        val messages = mutableListOf<String>()
        var isValid = true

        if (!Validator.isUUIDValid(UUID)) {
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

        OutlinedTextField(
            value = UUID,
            onValueChange = { UUID = it },
            label = { Text(text = "Verification code") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (validateForm()) {
                val url = "http://10.0.2.2:8080/api/v1/users/verify"
                val params = HashMap<String, String>()
                params["email"] = email
                params["uuid"] = UUID

                val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,
                    url,
                    JSONObject(params as Map<*, *>),
                    { response ->
                        println("Verification successful: $response")
                        navController.navigate(LOGIN_ROUTE)
                    },
                    { error ->
                        println("Verification error: ${error.message}")
                    })

                VolleySingleton.getRequestQueue().add(jsonObjectRequest)

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