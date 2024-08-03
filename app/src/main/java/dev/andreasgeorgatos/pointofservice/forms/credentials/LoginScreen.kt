package dev.andreasgeorgatos.pointofservice.forms.credentials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.andreasgeorgatos.pointofservice.FORGOT_PASSWORD_ROUTE
import dev.andreasgeorgatos.pointofservice.REGISTER_ROUTE
import dev.andreasgeorgatos.pointofservice.forms.TextInputField

@Composable
fun LoginScreen(navController: NavController) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showAlertDialog by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    fun validateForm(): Boolean {
        val fields = mapOf("E-mail" to userName, "Password" to password)
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

        Text(text = "Login to your account")

        Spacer(modifier = Modifier.height(32.dp))

        TextInputField(
            value = userName,
            onValueChange = { userName = it },
            label = "user name",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInputField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (validateForm()) {
                // Proceed with login logic
            } else {
                showAlertDialog = true
            }
        }) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate(REGISTER_ROUTE) }) {
            Text("Register")
        }

        TextButton(onClick = { navController.navigate(FORGOT_PASSWORD_ROUTE) }) {
            Text("Forgot Password")
        }
    }

    ValidationAlertDialog(
        showDialog = showAlertDialog,
        onDismiss = { showAlertDialog = false },
        message = alertMessage
    )
}
