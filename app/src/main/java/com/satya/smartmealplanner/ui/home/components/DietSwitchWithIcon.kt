package com.satya.smartmealplanner.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DietSwitchWithIcon(onCheckedChange: (Boolean) -> Unit) {

    var checked by remember { mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (checked) "Veg" else "Non\nVeg",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .padding(horizontal = 4.dp),
            lineHeight = 14.sp
        )

        Box(modifier = Modifier.height(22.dp)) {
            Switch(
                modifier = Modifier
                    .scale(0.7f)
                    .align(Alignment.Center),
                checked = checked,
                onCheckedChange = {
                    checked = it
                    onCheckedChange(checked)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green.copy(alpha = 0.8f),
                    checkedTrackColor = Color.LightGray.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.Red.copy(alpha = 0.8f),
                    uncheckedTrackColor = Color.LightGray.copy(alpha = 0.5f),
                ),
            )
        }

    }
}
