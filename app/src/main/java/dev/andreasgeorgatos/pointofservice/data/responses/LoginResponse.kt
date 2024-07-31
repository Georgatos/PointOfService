package dev.andreasgeorgatos.pointofservice.data.responses

import dev.andreasgeorgatos.pointofservice.data.dto.UserDTO

data class LoginResponse(
    val success: Boolean,
    val message: String? = null,
    val token: String? = null,
    val user: UserDTO? = null

)
