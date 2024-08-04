package dev.andreasgeorgatos.pointofservice.forms.credentials

import android.util.Log
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
import dev.andreasgeorgatos.pointofservice.ADMIN_MAIN_SCREEN
import dev.andreasgeorgatos.pointofservice.COOK_HELPER_MAIN_SCREEN
import dev.andreasgeorgatos.pointofservice.COOK_MAIN_SCREEN
import dev.andreasgeorgatos.pointofservice.CUSTOMER_MAIN_SCREEN
import dev.andreasgeorgatos.pointofservice.FORGOT_PASSWORD_ROUTE
import dev.andreasgeorgatos.pointofservice.MANAGER_MAIN_SCREEN
import dev.andreasgeorgatos.pointofservice.REGISTER_ROUTE
import dev.andreasgeorgatos.pointofservice.SERVER_MAIN_SCREEN
import dev.andreasgeorgatos.pointofservice.data.dto.CredentialsDTO
import dev.andreasgeorgatos.pointofservice.data.dto.PermissionDTO
import dev.andreasgeorgatos.pointofservice.data.dto.UserNameDTO
import dev.andreasgeorgatos.pointofservice.forms.TextInputField
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


interface PermissionsCallback {
    fun onPermissionsReceived(permissions: List<String>)
}

fun getAllUserPermissions(userName: String, callback: PermissionsCallback) {
    RetrofitClient.userService.getUserPermissions(UserNameDTO(userName)).enqueue(
        object : Callback<List<PermissionDTO>> {
            override fun onResponse(
                call: Call<List<PermissionDTO>>,
                response: Response<List<PermissionDTO>>
            ) {
                if (response.isSuccessful) {
                    val permissions = response.body()?.map { it.authority } ?: emptyList()
                    callback.onPermissionsReceived(permissions)
                } else {
                    callback.onPermissionsReceived(emptyList())
                }
            }

            override fun onFailure(call: Call<List<PermissionDTO>>, t: Throwable) {
                callback.onPermissionsReceived(emptyList())
            }
        })
}


fun redirectUser(userPermissions: List<String>, navController: NavController) {
    Log.d("Permissions", userPermissions.toString())
    Log.d("Permissions", userPermissions.contains("ROLE_CUSTOMER").toString())

    if (userPermissions.contains("ROLE_ADMIN")) {
        navController.navigate(ADMIN_MAIN_SCREEN)
    }
    if (userPermissions.contains("ROLE_MANAGER")) {
        navController.navigate(MANAGER_MAIN_SCREEN)
    }
    if (userPermissions.contains("ROLE_SERVER")) {
        navController.navigate(SERVER_MAIN_SCREEN)
    }
    if (userPermissions.contains("ROLE_COOK")) {
        navController.navigate(COOK_MAIN_SCREEN)
    }
    if (userPermissions.contains("ROLE_COOK_HELPER")) {
        navController.navigate(COOK_HELPER_MAIN_SCREEN)
    }
    if (userPermissions.contains("ROLE_CUSTOMER")) {
        navController.navigate(CUSTOMER_MAIN_SCREEN)
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showAlertDialog by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    fun validateForm(): Boolean {
        val fields = mapOf(userName to userName, "Password" to password)
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
                RetrofitClient.userService.loginUser(CredentialsDTO(userName, password))
                    .enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                getAllUserPermissions(userName, object : PermissionsCallback {
                                    override fun onPermissionsReceived(permissions: List<String>) {
                                        redirectUser(permissions, navController)
                                    }
                                })
                            } else {
                                alertMessage = "Login failed"
                                showAlertDialog = true
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            alertMessage = "Login failed"
                            showAlertDialog = true
                        }
                    })
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
