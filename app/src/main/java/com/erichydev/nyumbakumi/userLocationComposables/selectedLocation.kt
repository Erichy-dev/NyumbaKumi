package com.erichydev.nyumbakumi.userLocationComposables

import androidx.compose.ui.graphics.Color
import android.graphics.PointF
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.erichydev.nyumbakumi.homeComposables.HomeViewModel

@Composable
fun SelectedUserLocation(
    homeViewModel: HomeViewModel
) {
    var selectedLocationOption by remember {
        mutableStateOf("Kiganjo")
    }

    LazyColumn(
        modifier = Modifier
            .drawBehind {
                val strokeWidth = 1.dp.toPx() // Define the thickness of the border
                drawLine(
                    color = Color(0xFF073B4C), // Color of the border
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
            .padding(end = 5.dp)
            .heightIn(max = 200.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        item {
            LocationButton(
                selectedLocationOption = selectedLocationOption,
                currentLocation = "Kiganjo",
                onLocationSelected = { location -> selectedLocationOption = location },
                homeViewModel
            )
        }
        item {
            LocationButton(
                selectedLocationOption = selectedLocationOption,
                currentLocation = "Makongeni",
                onLocationSelected = { location -> selectedLocationOption = location },
                homeViewModel
            )
        }
        item {
            LocationButton(
                selectedLocationOption = selectedLocationOption,
                currentLocation = "Weiteithie",
                onLocationSelected = { location -> selectedLocationOption = location },
                homeViewModel
            )
        }
        item {
            LocationButton(
                selectedLocationOption = selectedLocationOption,
                currentLocation = "Runda",
                onLocationSelected = { location -> selectedLocationOption = location },
                homeViewModel
            )
        }
    }
}