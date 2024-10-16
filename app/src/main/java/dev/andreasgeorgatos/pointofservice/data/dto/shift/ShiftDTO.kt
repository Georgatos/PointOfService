package dev.andreasgeorgatos.pointofservice.data.dto.shift

import java.time.LocalDate

data class ShiftDTO(
    val id: Long,
    val userId: Long,
    val roles: List<String>,
    val shiftStart: LocalDate,
    val shiftEnd: LocalDate,
    val startedById: Long,
    val startedByMethod: String,
    val endedById: Long,
    val endedByMethod: String,
    val pastShiftLogsId: Set<ShiftLogsDTO>
)
