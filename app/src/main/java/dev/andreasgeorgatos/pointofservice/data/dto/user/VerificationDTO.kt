package dev.andreasgeorgatos.pointofservice.data.dto.user

data class VerificationDTO(
    val email: String,
    val verificationCode: String
)
