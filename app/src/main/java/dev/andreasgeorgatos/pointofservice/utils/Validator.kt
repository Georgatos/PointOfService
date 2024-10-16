package dev.andreasgeorgatos.pointofservice.utils

object Validator {
    fun isEmailValid(input: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }

    fun isPasswordValid(input: String): Boolean {
        return input.length >= 9
    }

    fun isStringValid(input: String): Boolean {
        return input.isNotEmpty()
    }

    fun isPhoneNumberValid(input: String): Boolean {
        return input.length == 10
    }

    fun isDigitValid(input: String): Boolean {
        return input.isNotEmpty() && input.all { it.isDigit() }
    }

    fun isUUIDValid(input: String): Boolean {
        return input.length == 36
    }

    fun isDigitOrCommaValid(input: String): Boolean {
        return input.isNotEmpty() && input.all { it.isDigit() || it == ',' }
    }
}