package com.erichydev.nyumbakumi.userLocationComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erichydev.nyumbakumi.homeComposables.HomeViewModel

@Composable
fun LocationButton(
    selectedLocationOption: String,
    currentLocation: String,
    onLocationSelected: (String) -> Unit,
    homeViewModel: HomeViewModel
) {
    val backgroundColor = if (selectedLocationOption == currentLocation) {
        Color(0xFF073B4C)
    } else {
        Color.Transparent
    }

    val shape = if (selectedLocationOption == currentLocation) {
        RoundedCornerShape(8.dp)
    } else {
        RoundedCornerShape(0.dp)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onLocationSelected(currentLocation)
                homeViewModel.setSelectedLocationOption(currentLocation)
            }
            .background(color = backgroundColor, shape = shape)
            .padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.height(20.dp),
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF06D6A0),
                unselectedColor = Color(0xFF073B4C)
            ),
            selected = selectedLocationOption == currentLocation,
            onClick = {
                onLocationSelected(currentLocation)
                homeViewModel.setSelectedLocationOption(currentLocation)
            }
        )

        Text(
            text = currentLocation,
            fontSize = 16.sp,
            color = Color(0xFFC0C0C0),
            fontWeight = FontWeight.Normal
        )
    }
}