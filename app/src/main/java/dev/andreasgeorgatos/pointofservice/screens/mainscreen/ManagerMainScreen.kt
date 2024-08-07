package dev.andreasgeorgatos.pointofservice.screens.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.andreasgeorgatos.pointofservice.ADD_TABLES_SCREEN
import dev.andreasgeorgatos.pointofservice.REMOVE_TABLES_SCREEN


@Composable
fun ManagerMainScreen(navController: NavController) {
    var amount = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "What do you want to do?", style = TextStyle(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { navController.navigate(ADD_TABLES_SCREEN) }, Modifier.weight(1f)) {
                Text(text = "Add Tables")
            }
            Button(onClick = { navController.navigate(REMOVE_TABLES_SCREEN) }, Modifier.weight(1f)) {
                Text(text = "Remove Tables")
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { /*TODO*/ }, Modifier.weight(1f)) {
                Text(text = "Start shift")
            }
            Button(onClick = { /*TODO*/ }, Modifier.weight(1f)) {
                Text(text = "End shift")
            }
        }
    }
}