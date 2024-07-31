package dev.andreasgeorgatos.pointofservice.data.dto

import java.time.LocalDate

data class UserDTO(
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val city: String,
    val address: String,
    val postalCode: String,
    val doorRingBellName: String,
    val phoneNumber: String,

    val addressNumber: Long,
    val storyLevel: Long,
    val birthDate: LocalDate
)
