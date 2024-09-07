package com.erichydev.nyumbakumi.homeComposables.filters

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erichydev.nyumbakumi.homeComposables.HomeViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ApplyFilter(
    viewModel: HomeViewModel,
    func: () -> Unit
) {
    val gradientColors = listOf(
        Color(0xFFbf73e6),
        Color(0xFF40aee9),
        Color(0xFFe55a8b),
        Color(0xFF679ceb),
        Color(0xFFf0536a),
    )
    val gradientBrush = Brush.linearGradient(
        colors = gradientColors
    )

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null,
                    onClick = {
                        func()
                        viewModel.filterPlots(context)
                    }
                )
                .background(Color(0xFF073B4C), shape = RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    gradientBrush,
                    shape = RoundedCornerShape(10.dp)

                )
                .padding(horizontal = 30.dp, vertical = 12.dp)
        ) {
            Text(
                text = "APPLY",
                fontSize = 13.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}