package dev.andreasgeorgatos.pointofservice.data.dto.employee

data class EmployeeDTO(
    val userId: Long,
    val userName: String,
    val imageUrl: String,
    val firstName: String,
    val lastName: String,
    val roles: List<String>
)