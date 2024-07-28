package dev.andreasgeorgatos.pointofservice.loginform

import VolleySingleton
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import dev.andreasgeorgatos.pointofservice.LOGIN_ROUTE
import dev.andreasgeorgatos.pointofservice.VERIFY_EMAIL_ROUTE
import dev.andreasgeorgatos.pointofservice.utils.Validator
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(navController: NavController) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var addressNumber by remember { mutableStateOf("") }
    var storyLevel by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var doorRingBellName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    var showAlertDialog by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }


    fun validateForm(): Boolean {
        val messages = mutableListOf<String>()
        var isValid = true

        if (!Validator.isNameValid(firstName)) {
            messages.add("First name is required.")
            isValid = false
        }
        if (!Validator.isNameValid(lastName)) {
            messages.add("Last name is required.")
            isValid = false
        }
        if (!Validator.isEmailValid(email)) {
            messages.add("Email is invalid.")
            isValid = false
        }
        if (!Validator.isPasswordValid(password)) {
            messages.add("Password must be at least 8 characters.")
            isValid = false
        }
        if (!Validator.isPhoneNumberValid(phoneNumber)) {
            messages.add("Phone number is invalid.")
            isValid = false
        }
        if (!Validator.isCityValid(city)) {
            messages.add("City is required.")
            isValid = false
        }
        if (!Validator.isAddressValid(address)) {
            messages.add("Address is required.")
            isValid = false
        }
        if (!Validator.isAddressNumberValid(addressNumber)) {
            messages.add("Address number is required.")
            isValid = false
        }
        if (!Validator.isStoryLevelValid(storyLevel)) {
            messages.add("Story level must be a number.")
            isValid = false
        }
        if (!Validator.isPostalCodeValid(postalCode)) {
            messages.add("Postal code must be a number.")
            isValid = false
        }
        if (!Validator.isDoorRingBellNameValid(doorRingBellName)) {
            messages.add("Doorbell name is required.")
            isValid = false
        }

        alertMessage = messages.joinToString("\n")
        return isValid

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register an account")

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = if (birthDate != LocalDate.MIN) birthDate.format(
                    DateTimeFormatter.ofPattern(
                        "yyyy/MM/dd"
                    )
                ) else "",
                onValueChange = { },
                label = { Text("Birth Date") },
                modifier = Modifier
                    .weight(1f)
                    .clickable { showDatePicker = true },
            )

        }

        if (showDatePicker) {
            val context = LocalContext.current
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    birthDate = LocalDate.of(year, month + 1, dayOfMonth)
                    showDatePicker = false
                },
                if (birthDate != LocalDate.MIN) birthDate.year else LocalDate.now().year,
                if (birthDate != LocalDate.MIN) birthDate.monthValue - 1 else LocalDate.now().monthValue - 1,
                if (birthDate != LocalDate.MIN) birthDate.dayOfMonth else LocalDate.now().dayOfMonth
            ).show()
        }


        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.weight(2f)
            )
            OutlinedTextField(value = addressNumber,
                onValueChange = { addressNumber = it },
                label = { Text("No.") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = storyLevel,
                onValueChange = { storyLevel = it },
                label = { Text("Story") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(value = city,
                onValueChange = { city = it },
                label = { Text("City") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(value = postalCode,
                onValueChange = { postalCode = it },
                label = { Text("Postal Code") },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(value = doorRingBellName,
            onValueChange = { doorRingBellName = it },
            label = { Text("Doorbell Name") },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            if (validateForm()) {
                val url = "http://10.0.2.2:8080/api/v1/users/register"

                val params = HashMap<String, String>()
                params["firstName"] = firstName
                params["lastName"] = lastName
                params["password"] = password
                params["email"] = email
                params["phoneNumber"] = phoneNumber
                params["city"] = city
                params["address"] = address
                params["addressNumber"] = addressNumber
                params["postalCode"] = postalCode
                params["storyLevel"] = storyLevel
                params["doorRingBellName"] = doorRingBellName
                params["birthDate"] = birthDate.format(DateTimeFormatter.ISO_DATE)

                val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,
                    url,
                    JSONObject(params as Map<*, *>),
                    { response ->
                        println("Registration successful: $response")
                        navController.navigate("$VERIFY_EMAIL_ROUTE/$email")
                    },
                    { error ->
                        println("Registration error: ${error}")
                    })
                VolleySingleton.getRequestQueue().add(jsonObjectRequest)
            } else {
                showAlertDialog = true
            }
        }) {
            Text("Register")
        }

        TextButton(onClick = { navController.navigate(LOGIN_ROUTE) }) {
            Text("Login")
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