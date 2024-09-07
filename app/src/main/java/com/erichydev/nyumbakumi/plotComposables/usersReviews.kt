package com.erichydev.nyumbakumi.plotComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erichydev.nyumbakumi.appComposables.PlotRatingStars
import com.erichydev.nyumbakumi.data.Plot
import com.erichydev.nyumbakumi.data.UserRateReview
import com.erichydev.nyumbakumi.ui.theme.DescriptionTheme
import com.erichydev.nyumbakumi.ui.theme.NormalTheme

@Composable
fun UsersReviews(
    plotRating: UserRateReview
) {
    Column(
        modifier = Modifier
            .background(Color(0xFF000000), shape = RoundedCornerShape(8.dp))
            .padding(20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFF073b4c), shape = CircleShape)
                        .size(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    NormalTheme {
                        Text(
                            text = "${plotRating.userName[0]}",
                            fontFamily = FontFamily.Cursive,
                            fontSize = 18.sp
                        )
                    }
                }

                DescriptionTheme {
                    Text(
                        text = plotRating.userName,
                        maxLines = 1,
                        modifier = Modifier.width(80.dp),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            PlotRatingStars(plotRating.rating)
        }

        DescriptionTheme {
            Text(plotRating.review)
        }
    }
}