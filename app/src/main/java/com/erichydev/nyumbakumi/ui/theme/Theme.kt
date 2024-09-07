package com.erichydev.nyumbakumi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Black,
    secondary = Black,
    tertiary = Black
)

@Composable
fun DescriptionTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = DescriptionTypography,
        content = content
    )
}

@Composable
fun TitleTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = TitleTypography,
        content = content
    )
}

@Composable
fun NormalTheme(
    content: @Composable () -> Unit,
){
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content,
        typography = normalTypography
    )
}

@Composable
fun LinkText(
    content: @Composable () -> Unit
){
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content,
        typography = linkTypography
    )
}

@Composable
fun SubTitleTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content,
        typography = subTitleTypography
    )
}