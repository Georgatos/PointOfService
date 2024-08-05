package dev.andreasgeorgatos.pointofservice.forms.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.andreasgeorgatos.pointofservice.R


@Composable
fun SystemMainScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Choose your language",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(32.dp),
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.greek_flag),
                contentDescription = "Translate content to Greek",
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .clickable(onClick = {
                        //Handle language
                    })
            )
            Image(
                painter = painterResource(id = R.drawable.uk_flag),
                contentDescription = "Translate content to English",
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .clickable(onClick = {
                    })
            )
        }
    }
}