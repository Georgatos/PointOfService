package dev.andreasgeorgatos.pointofservice.data.dto.shift

import java.time.LocalDate

data class ShiftLogsDTO(
    val id: Long,
    val triggeredActionAt: LocalDate,
    val action: String,
    val performedById: Long,
    val method: String,
    val shiftId: Long
)
