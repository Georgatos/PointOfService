package dev.andreasgeorgatos.pointofservice.forms.credentials.registerform

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import dev.andreasgeorgatos.pointofservice.LOGIN_ROUTE
import dev.andreasgeorgatos.pointofservice.VERIFY_EMAIL_ROUTE
import dev.andreasgeorgatos.pointofservice.data.dto.UserDTO
import dev.andreasgeorgatos.pointofservice.forms.TextInputField
import dev.andreasgeorgatos.pointofservice.forms.credentials.FormValidator
import dev.andreasgeorgatos.pointofservice.forms.credentials.ValidationAlertDialog
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(navController: NavController) {
    val context: Context = LocalContext.current

    val (firstName, setFirstName) = remember { mutableStateOf("") }
    val (lastName, setLastName) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (email, setEmail) = remember { mutableStateOf("") }
    val (city, setCity) = remember { mutableStateOf("") }
    val (address, setAddress) = remember { mutableStateOf("") }
    val (addressNumber, setAddressNumber) = remember { mutableStateOf("") }
    val (storyLevel, setStoryLevel) = remember { mutableStateOf("") }
    val (postalCode, setPostalCode) = remember { mutableStateOf("") }
    val (doorRingBellName, setDoorRingBellName) = remember { mutableStateOf("") }
    val (phoneNumber, setPhoneNumber) = remember { mutableStateOf("") }
    val (birthDate, setBirthDate) = remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    var showAlertDialog by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    fun validateForm(): Boolean {
        val fields = mapOf(
            "First Name" to firstName,
            "Last Name" to lastName,
            "E-mail" to email,
            "Password" to password,
            "Phone Number" to phoneNumber,
            "City" to city,
            "Address" to address,
            "Address Number" to addressNumber,
            "Story Level" to storyLevel,
            "Postal Code" to postalCode,
            "Door Ring" to doorRingBellName
        )
        val errors = FormValidator.validate(fields)

        if (errors.isNotEmpty()) {
            alertMessage = FormValidator.errorsToString(errors)
            Log.d("RegisterScreen", "Validation errors: $alertMessage")
            return false
        }

        Log.d("RegisterScreen", "Form validation passed")
        return true
    }


    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        calendar.time = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                setBirthDate(LocalDate.of(year, month + 1, dayOfMonth))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register an account", style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextInputField(
                value = firstName,
                onValueChange = setFirstName,
                label = "First Name",
                modifier = Modifier.weight(1f)
            )
            TextInputField(
                value = lastName,
                onValueChange = setLastName,
                label = "Last Name",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextInputField(
                value = email,
                onValueChange = setEmail,
                label = "Email",
                modifier = Modifier.weight(1f)
            )
            TextInputField(
                value = password,
                onValueChange = setPassword,
                visualTransformation = PasswordVisualTransformation(),
                label = "Password",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextInputField(
                value = phoneNumber,
                onValueChange = setPhoneNumber,
                label = "Phone",
                modifier = Modifier.weight(1f)
            )
            TextInputField(
                value = birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                onValueChange = { },
                label = "Birth Date",
                modifier = Modifier
                    .weight(1f)
                    .clickable { showDatePicker() },
                readOnly = true
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextInputField(
                value = address,
                onValueChange = setAddress,
                label = "Address",
                modifier = Modifier.weight(2f)
            )
            TextInputField(
                value = addressNumber,
                onValueChange = setAddressNumber,
                label = "No.",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextInputField(
                value = storyLevel,
                onValueChange = setStoryLevel,
                label = "Story",
                modifier = Modifier.weight(1f)
            )
            TextInputField(
                value = city,
                onValueChange = setCity,
                label = "City",
                modifier = Modifier.weight(1f)
            )
            TextInputField(
                value = postalCode,
                onValueChange = setPostalCode,
                label = "Postal Code",
                modifier = Modifier.weight(1f)
            )
        }

        TextInputField(
            value = doorRingBellName,
            onValueChange = setDoorRingBellName,
            label = "Doorbell Name",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (validateForm()) {
                val user = UserDTO(
                    firstName = firstName,
                    lastName = lastName,
                    password = password,
                    email = email,
                    city = city,
                    address = address,
                    addressNumber = addressNumber.toLongOrNull() ?: 0L,
                    postalCode = postalCode,
                    storyLevel = storyLevel.toLongOrNull() ?: 0L,
                    doorRingBellName = doorRingBellName,
                    phoneNumber = phoneNumber,
                    birthDate = birthDate
                )

                val gson = GsonBuilder()
                    .registerTypeAdapter(
                        LocalDate::class.java,
                        JsonSerializer<LocalDate> { src, _, _ ->
                            JsonPrimitive(
                                src.format(DateTimeFormatter.ISO_LOCAL_DATE)
                            )
                        })
                    .registerTypeAdapter(
                        LocalDate::class.java,
                        JsonDeserializer { json, _, _ ->
                            LocalDate.parse(
                                json.asString,
                                DateTimeFormatter.ISO_LOCAL_DATE
                            )
                        })
                    .create()

                val json = gson.toJson(user)
                Log.d("RegisterScreen", "BirthDate: $birthDate")
                Log.d("RegisterScreen", "JSON payload: $json")
                val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

                RetrofitClient.userService.registerUser(requestBody)
                    .enqueue(object : Callback<Void> {
                        override fun onResponse(
                            call: Call<Void>,
                            response: Response<Void>
                        ) {
                            Log.d("RegisterScreen", "Response code: ${response.code()}")
                            Log.d("RegisterScreen", "Response body: ${response.body()}")


                            if (response.isSuccessful) {
                                Log.d(
                                    "RegisterScreen",
                                    "Registration successful: ${response.body()}"
                                )
                                navController.navigate("$VERIFY_EMAIL_ROUTE/$email")
                            } else {
                                val errorResponse = response.errorBody()?.string()
                                    ?: "Unknown error"
                                alertMessage = errorResponse
                                showAlertDialog = true
                                Log.d("RegisterScreen", "Registration failed: $errorResponse")
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("RegisterScreen", "Registration Failure: ${t.message}")
                            alertMessage = "Registration failed: ${t.message}"
                            showAlertDialog = true
                        }
                    })
            } else {
                showAlertDialog = true
            }
        }) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(LOGIN_ROUTE)
        }) {
            Text(text = "Login")
        }
    }

    ValidationAlertDialog(
        showDialog = showAlertDialog,
        onDismiss = { showAlertDialog = false },
        message = alertMessage
    )
}
