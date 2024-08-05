package dev.andreasgeorgatos.pointofservice.forms.management.tables.remove

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.andreasgeorgatos.pointofservice.ADD_MULTIPLE_TABLE_SCREEN
import dev.andreasgeorgatos.pointofservice.ADD_SINGLE_TABLE_SCREEN


@Composable
fun RemoveTablesScreen(navController: NavController) {

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                navController.navigate(ADD_SINGLE_TABLE_SCREEN)
            }, Modifier.weight(1f)) {
                Text(text = "Remove Single Table")
            }
            Button(
                onClick = { navController.navigate(ADD_MULTIPLE_TABLE_SCREEN) },
                Modifier.weight(1f)
            ) {
                Text(text = "Remove multiple tables")
            }
        }
    }
}