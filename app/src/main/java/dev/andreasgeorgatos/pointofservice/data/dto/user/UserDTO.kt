package dev.andreasgeorgatos.pointofservice.data.dto.user

import java.time.LocalDate

data class UserDTO(
    val firstName: String,
    val lastName: String,
    val userName: String,
    val password: String,
    val email: String,
    val country: String,
    val city: String,
    val street: String,
    val postalCode: String,
    val doorRingBellName: String,
    val phoneNumber: String,
    val number: String,

    val storyLevel: Long,

    val birthDate: LocalDate
)
