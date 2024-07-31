package dev.andreasgeorgatos.pointofservice.data

data class ValidationError(
    val field: String,
    val message: String
)
