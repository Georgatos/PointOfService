package dev.andreasgeorgatos.pointofservice.data.dto.user

data class EmployeeDTO(
    val userId: Long,
    val userName: String,
    val roles: List<String>
)
