package dev.andreasgeorgatos.pointofservice.loginform.registerform

import dev.andreasgeorgatos.pointofservice.data.ValidationError
import dev.andreasgeorgatos.pointofservice.utils.Validator

object FormValidator {
    fun validate(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phoneNumber: String,
        city: String,
        address: String,
        addressNumber: String,
        storyLevel: String,
        postalCode: String,
        doorRingBellName: String,
    ): List<ValidationError> {
        val errors = mutableListOf<ValidationError>()

        if (!Validator.isNameValid(firstName)) {
            errors.add(ValidationError("First Name", "First name is required."))
        }
        if (!Validator.isNameValid(lastName)) {
            errors.add(ValidationError("Last Name", "Last name is required."))
        }
        if (!Validator.isEmailValid(email)) {
            errors.add(ValidationError("E-mail", "The E-mail is required."))
        }
        if (!Validator.isPasswordValid(password)) {
            errors.add(ValidationError("Password", "The password is required."))
        }
        if (!Validator.isPhoneNumberValid(phoneNumber)) {
            errors.add(ValidationError("Phone Number", "The Phone Number is required."))
        }
        if (!Validator.isCityValid(city)) {
            errors.add(ValidationError("City", "The City is required."))
        }
        if (!Validator.isAddressValid(address)) {
            errors.add(ValidationError("Address", "The address is required."))
        }
        if (!Validator.isAddressNumberValid(addressNumber)) {
            errors.add(ValidationError("Address Number", "The Address Number is required."))
        }
        if (!Validator.isStoryLevelValid(storyLevel)) {
            errors.add(ValidationError("Story Level", "The Story Level is required."))
        }
        if (!Validator.isPostalCodeValid(postalCode)) {
            errors.add(ValidationError("Postal Code", "The Postal Code is required."))
        }
        if (!Validator.isDoorRingBellNameValid(doorRingBellName)) {
            errors.add(ValidationError("Door Ring", "The Door Ring is required."))
        }

        return errors
    }
}
