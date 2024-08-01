package dev.andreasgeorgatos.pointofservice.data.dto

data class ResetPasswordDTO(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val token: String
)
