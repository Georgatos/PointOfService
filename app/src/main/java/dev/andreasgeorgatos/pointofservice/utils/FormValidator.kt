package dev.andreasgeorgatos.pointofservice.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import dev.andreasgeorgatos.pointofservice.data.ValidationError

object FormValidator {
    fun validate(fields: Map<String, String>): List<ValidationError> {
        val errors = mutableListOf<ValidationError>()

        fields.forEach { (field, value) ->
            when (field) {
                "E-mail" -> if (!Validator.isEmailValid(value)) {
                    errors.add(ValidationError(field, "The $field is required and must be of type e-mail."))
                }

                "Password" -> if (!Validator.isPasswordValid(value)) {
                    errors.add(
                        ValidationError(
                            field, "The $field is required and must be 9 characters long at least."
                        )
                    )
                }

                "Phone Number" -> if (!Validator.isPhoneNumberValid(value)) {
                    errors.add(ValidationError(field, "The $field is required and must be 10 character long."))
                }

                "Digit", "Number", "Story level", "Postal code" -> if (!Validator.isDigitValid(value)) {
                    errors.add(ValidationError(field, "The $field is required and must be a number."))
                }

                "First Name", "Last Name", "Country", "City", "Street", "Doorbell Name" -> if (!Validator.isStringValid(value)) {
                    errors.add(ValidationError(field, "The $field is required."))
                }

                "Verification Code" -> if (!Validator.isUUIDValid(value)) {
                    errors.add(ValidationError(field, "The $field isn't correct, please check your e-mail, it's a UUID."))
                }

                "Digit Or Comma" -> if (!Validator.isDigitOrCommaValid(value)) {
                    errors.add(ValidationError(field, "The $field isn't correct, must be a digit or a digit followed by comma."))
                }
            }
        }

        return errors
    }

    fun errorsToString(errors: List<ValidationError>): String {
        return errors.joinToString("\n") { "${it.field}: ${it.message}" }
    }
}

@Composable
fun ValidationAlertDialog(
    showDialog: Boolean, onDismiss: () -> Unit, message: String
) {
    if (showDialog) {
        AlertDialog(onDismissRequest = onDismiss, confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }, title = { Text("Validation Errors") }, text = { Text(message) })
    }
}

