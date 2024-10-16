package dev.andreasgeorgatos.pointofservice.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.andreasgeorgatos.pointofservice.R
import dev.andreasgeorgatos.pointofservice.data.dto.employee.EmployeeDTO

@Composable
fun EmployeeItem(employee: EmployeeDTO, onClick: (EmployeeDTO) -> Unit) {
    Column(modifier = Modifier
        .padding(16.dp)
        .clickable { onClick(employee) }) {

        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "ID: ${employee.userId}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "User name: ${employee.userName}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "First Name:  ${employee.firstName}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Last Name: ${employee.lastName}", style = MaterialTheme.typography.bodyLarge)
    }
}
