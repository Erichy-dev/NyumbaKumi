package com.erichydev.nyumbakumi.plotComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.erichydev.nyumbakumi.data.Plot
import com.erichydev.nyumbakumi.data.UserRateReview
import com.erichydev.nyumbakumi.signInComposables.AnimatedButton
import com.erichydev.nyumbakumi.ui.theme.DescriptionTheme
import com.erichydev.nyumbakumi.ui.theme.NormalTheme
import com.google.firebase.auth.FirebaseUser

@Composable
fun UserRateReview(
    user: FirebaseUser?,
    viewModel: RateReviewViewModel,
    plot: Plot
) {
    val context = LocalContext.current
    var review by remember { mutableStateOf("") }

    val gradientColors = listOf(
        Color(0xFF6cb4c4),
        Color(0xFF8d8da9),
        Color(0xFFa86b92),
        Color(0xFFc84376),
        Color(0xFFe22361),
    )

    // Create a linear gradient brush
    val gradientBrush = Brush.linearGradient(
        colors = gradientColors
    )

    var userRate by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .background(Color(0xFF000000), shape = RoundedCornerShape(20.dp))
            .padding(20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724833567/nyumba_kumi/icons/rate_lvukqw.png")
                        .decoderFactory(SvgDecoder.Factory())
                        .crossfade(true)
                        .build(),
                    contentDescription = "home",
                    modifier = Modifier
                        .size(40.dp)
                )

                NormalTheme {
                    Text(
                        text = user?.displayName ?: "",
                        modifier = Modifier.width(80.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily.Cursive,
                        fontSize = 22.sp,
                        style = TextStyle(
                            brush = gradientBrush
                        )
                    )
                }
            }

            UserRating(userRate) { rate -> userRate = rate }
        }

        TextField(
            value = review,
            onValueChange = { review = it }, // Update the state when the text changes
            label = {
                Text(
                    text = "Review the plot"
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedLabelColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF118ab2),
                selectionColors = TextSelectionColors(handleColor = Color(0xFF118ab2), backgroundColor = Color(0xFF073b4c))
            ),
            leadingIcon = {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://res.cloudinary.com/dgaqws2ux/image/upload/v1724845782/nyumba_kumi/icons/review_ue6wc9.png")
                        .decoderFactory(SvgDecoder.Factory())
                        .crossfade(true)
                        .build(),
                    contentDescription = "cash",
                    modifier = Modifier
                        .size(50.dp)
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
        )

        AnimatedButton(
            func = {
//                viewModel.addPlotReview(UserRateReview(plot.plotNumber, user?.email ?: "", user?.displayName ?: "", userRate, review))
                viewModel.publishReview(context, plot.plotNumber, user?.email ?: "", user?.displayName ?: "", userRate, review)
                userRate = 0
                review = ""
            },
            textContent = "*_Publish_*",
            gradientColors = gradientColors
        )
    }
}