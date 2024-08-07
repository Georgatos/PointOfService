package dev.andreasgeorgatos.pointofservice.data.dto.user

data class ResetPasswordDTO(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val token: String
)
