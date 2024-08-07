package dev.andreasgeorgatos.pointofservice.screens.credentials

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import dev.andreasgeorgatos.pointofservice.data.ValidationError
import dev.andreasgeorgatos.pointofservice.utils.Validator

object FormValidator {
    fun validate(fields: Map<String, String>): List<ValidationError> {
        val errors = mutableListOf<ValidationError>()

        fields.forEach { (field, value) ->
            when (field) {
                "First Name", "Last Name" -> if (!Validator.isNameValid(value)) {
                    errors.add(ValidationError(field, "$field is required."))
                }

                "E-mail" -> if (!Validator.isEmailValid(value)) {
                    errors.add(ValidationError(field, "The $field is required."))
                }

                "Password" -> if (!Validator.isPasswordValid(value)) {
                    errors.add(
                        ValidationError(
                            field, "The $field is required and must be 9 characters long at least."
                        )
                    )
                }

                "Phone Number" -> if (!Validator.isPhoneNumberValid(value)) {
                    errors.add(ValidationError(field, "The $field is required."))
                }

                "City" -> if (!Validator.isCityValid(value)) {
                    errors.add(ValidationError(field, "The $field is required."))
                }

                "Address" -> if (!Validator.isAddressValid(value)) {
                    errors.add(ValidationError(field, "The $field is required."))
                }

                "Address Number" -> if (!Validator.isAddressNumberValid(value)) {
                    errors.add(ValidationError(field, "The $field is required."))
                }

                "Digit" -> if (!Validator.isDigitValid(value)) {
                    errors.add(ValidationError(field, "The $field is required."))
                }

                "Postal Code" -> if (!Validator.isPostalCodeValid(value)) {
                    errors.add(ValidationError(field, "The $field is required."))
                }

                "Door Ring" -> if (!Validator.isDoorRingBellNameValid(value)) {
                    errors.add(ValidationError(field, "The $field is required."))
                }

                "Verification Code" -> if (!Validator.isUUIDValid(value)) {
                    errors.add(ValidationError(field, "The $field isn't correct"))
                }

                "Digit Or Comma" -> if (!Validator.isDigitOrCommaValid(value)) {
                    errors.add(ValidationError(field, "The $field isn't correct"))
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

