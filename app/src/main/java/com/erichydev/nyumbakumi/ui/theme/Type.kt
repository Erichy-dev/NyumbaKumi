package com.erichydev.nyumbakumi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val DescriptionTypography = Typography(
    bodyLarge = TextStyle(
        color = Color(0xFFedf2f4),
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center,
    ),
)

val TitleTypography = Typography(
    bodyLarge = TextStyle(
        color = Color(0xFFedf2f4),
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.Center
    ),
)

val normalTypography = Typography(
    bodyLarge = TextStyle(
        color = Color(0xFFf3f4f4),
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 0.5.sp,
    ),
)

val subTitleTypography = Typography(
    bodyLarge = TextStyle(
        color = Color(0xFFf3f4f4),
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)

val linkTypography = Typography(
    bodyLarge = TextStyle(
        color = Color(0xFF8d99ae),
        fontSize = 15.sp,
        letterSpacing = 0.5.sp
    ),
)
