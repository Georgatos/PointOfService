package dev.andreasgeorgatos.pointofservice.screens.management.employees

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.andreasgeorgatos.pointofservice.data.dto.employee.EmployeeDTO
import dev.andreasgeorgatos.pointofservice.items.EmployeeItem
import dev.andreasgeorgatos.pointofservice.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ShowAllEmployeesScreen(navController: NavController) {
    var employees by remember { mutableStateOf<Map<Long, EmployeeDTO>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        RetrofitClient.employeeService.getAllEmployees()
            .enqueue(object : Callback<Map<Long, EmployeeDTO>> {
                override fun onResponse(
                    call: Call<Map<Long, EmployeeDTO>>,
                    response: Response<Map<Long, EmployeeDTO>>
                ) {
                    if (response.isSuccessful) {
                        employees = response.body()
                    }
                }

                override fun onFailure(call: Call<Map<Long, EmployeeDTO>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    errorMessage?.let { message ->
        Text(text = "Error: $message")
    }

    employees?.let { employeeMap ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 512.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(employeeMap.values.toList()) { employee ->
                EmployeeItem(employee = employee) {
                    // Handle item click or interaction
                }
            }
        }
    }
}
