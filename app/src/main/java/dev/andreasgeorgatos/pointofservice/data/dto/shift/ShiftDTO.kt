package dev.andreasgeorgatos.pointofservice.data.dto.shift

data class ShiftDTO(
    val id: Long,
    val employeeId: Long,
    val roleId: Long,
    val shiftStart: String,
    val shiftEnd: String
)
