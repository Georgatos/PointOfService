package dev.andreasgeorgatos.pointofservice.screens.credentials.registerform

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import dev.andreasgeorgatos.pointofservice.data.dto.user.UserDTO
import dev.andreasgeorgatos.pointofservice.screens.TextInputField
import dev.andreasgeorgatos.pointofservice.utils.FormValidator
import dev.andreasgeorgatos.pointofservice.utils.ValidationAlertDialog
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
    val (userName, setUserName) = remember { mutableStateOf("")}
    val (email, setEmail) = remember { mutableStateOf("") }
    val (city, setCity) = remember { mutableStateOf("") }
    val (country, setCountry) = remember { mutableStateOf("") }
    val (street, setStreet) = remember { mutableStateOf("") }
    val (number, setNumber) = remember { mutableStateOf("") }
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
            "Password" to password,
            "StringData" to userName,
            "E-mail" to email,
            "Country" to country,
            "Phone Number" to phoneNumber,
            "City" to city,
            "Street" to street,
            "Number" to number,
            "Story level" to storyLevel,
            "Postal code" to postalCode,
            "Doorbell Name" to doorRingBellName
        )
        val errors = FormValidator.validate(fields)

        if (errors.isNotEmpty()) {
            alertMessage = FormValidator.errorsToString(errors)
            return false
        }

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
                value = userName,
                onValueChange = setUserName,
                label = "user name",
                modifier = Modifier.weight(1f)
            )

            TextInputField(
                value = phoneNumber,
                onValueChange = setPhoneNumber,
                label = "Phone",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextInputField(
                value = street,
                onValueChange = setStreet,
                label = "Street",
                modifier = Modifier.weight(2f)
            )
            TextInputField(
                value = number,
                onValueChange = setNumber,
                label = "Number",
                modifier = Modifier.weight(1f)
            )
            TextInputField(
                value = country,
                onValueChange = setCountry,
                label = "Country",
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

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextInputField(
                value = doorRingBellName,
                onValueChange = setDoorRingBellName,
                label = "Doorbell Name",
                modifier = Modifier.weight(1f)
            )

            TextInputField(
                value = birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                onValueChange = { /* no-op */ },
                label = "Birth Date",
                modifier = Modifier
                    .weight(1f)
                    .clickable { showDatePicker() },
                readOnly = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (validateForm()) {
                val user = UserDTO(
                    firstName = firstName,
                    lastName = lastName,
                    userName = userName,
                    password = password,
                    email = email,
                    country = country,
                    city = city,
                    street = street,
                    postalCode = postalCode,
                    doorRingBellName = doorRingBellName,
                    phoneNumber = phoneNumber,
                    number = number,
                    storyLevel = storyLevel.toLongOrNull() ?: 0L,
                    birthDate = birthDate
                )

                val gson = GsonBuilder()
                    .registerTypeAdapter(LocalDate::class.java, JsonSerializer<LocalDate> { src, _, _ ->
                        JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE))
                    })
                    .create()

                val json = gson.toJson(user)
                val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

                RetrofitClient.userService.registerUser(requestBody)
                    .enqueue(object : Callback<Void> {
                        override fun onResponse(
                            call: Call<Void>,
                            response: Response<Void>
                        ) {
                            if (response.isSuccessful) {

                                navController.navigate("$VERIFY_EMAIL_ROUTE/$email")
                            } else {
                                val errorResponse = response.errorBody()?.string()
                                    ?: "Unknown error"
                                alertMessage = errorResponse
                                showAlertDialog = true
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
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