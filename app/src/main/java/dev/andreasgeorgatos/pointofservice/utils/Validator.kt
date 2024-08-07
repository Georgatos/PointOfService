package dev.andreasgeorgatos.pointofservice.utils

object Validator {
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.length == 10
    }

    fun isCityValid(city: String): Boolean {
        return city.isNotEmpty()
    }

    fun isAddressValid(address: String): Boolean {
        return address.isNotEmpty()
    }

    fun isAddressNumberValid(addressNumber: String): Boolean {
        return addressNumber.isNotEmpty()
    }

    fun isDigitValid(storyLevel: String): Boolean {
        return storyLevel.isNotEmpty() && storyLevel.all { it.isDigit() }
    }

    fun isPostalCodeValid(postalCode: String): Boolean {
        return postalCode.isNotEmpty() && postalCode.all { it.isDigit() }
    }

    fun isDoorRingBellNameValid(doorRingBellName: String): Boolean {
        return doorRingBellName.isNotEmpty()
    }

    fun isUUIDValid(UUID: String): Boolean {
        return UUID.length == 36
    }

    fun isDigitOrCommaValid(input: String): Boolean {
        return input.isNotEmpty() && input.all { it.isDigit() || it == ',' }
    }
}