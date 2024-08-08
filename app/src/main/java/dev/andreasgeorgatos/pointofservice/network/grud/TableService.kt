package dev.andreasgeorgatos.pointofservice.network.grud

import dev.andreasgeorgatos.pointofservice.data.dto.tables.TableDTO
import dev.andreasgeorgatos.pointofservice.data.dto.tables.TableNumberDTO
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TableService {

    @POST("order/DineIn")
    fun createTable(@Body requestBody: RequestBody): Call<Void>

    @POST("order/DineIn/getDineInTableByNumber")
    fun getTableByTableNumber(@Body tableNumber: Long): Call<TableDTO>

    @POST("order/DineIn/deleteTableByNumber")
    fun deleteTableByTableNumber(@Body tableNumber: TableNumberDTO): Call<TableDTO>

}