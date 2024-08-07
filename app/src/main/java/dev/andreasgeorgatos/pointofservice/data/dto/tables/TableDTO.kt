package dev.andreasgeorgatos.pointofservice.data.dto.tables

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class TableDTO(
    val orderItemId: Int?,
    val tableNumber: Int,
    val status: String,
    val createdAt: String,
    val updatedAt: String
) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(tableNumber: Int) : this(
        orderItemId = null,
        tableNumber = tableNumber,
        status = "AVAILABLE",
        createdAt = LocalDate.now().toString(),
        updatedAt = LocalDate.now().toString()
    )
}
