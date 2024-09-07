package com.erichydev.nyumbakumi.plotComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erichydev.nyumbakumi.data.Plot
import com.google.firebase.auth.FirebaseUser

@Composable
fun PlotReviews(
    plot: Plot,
    user: FirebaseUser?
) {
    val context = LocalContext.current
    val viewModel: RateReviewViewModel = viewModel()
    val plotRatings by viewModel.plotRateReviews.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchPlotReviews(context, plot.plotNumber)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 100.dp, top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        if(!plotRatings.any{ it.userEmail == user?.email }) {
            UserRateReview(user, viewModel, plot)
        }

        plotRatings.forEach {plotRating ->
            UsersReviews(plotRating)
        }
    }
}

@Preview
@Composable
fun PreviewPlotReview() {
    val plot = Plot(
        "",
        "",
        "",
        "",
        "",
        "ajklsd",
        "",
        4000,
        "cool plot",
        false,
        false,
        true,
        false,
        false,
        "multi-storey",
        "multi-storey",
        "",
        "",
        "",
        4,
        "",
        "",
    )
    PlotReviews(plot, null)
}