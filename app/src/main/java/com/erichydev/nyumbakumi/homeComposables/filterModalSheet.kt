package com.erichydev.nyumbakumi.homeComposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.erichydev.nyumbakumi.homeComposables.filters.ApplyFilter
import com.erichydev.nyumbakumi.homeComposables.filters.FilterTitle
import com.erichydev.nyumbakumi.homeComposables.filters.PlotLocation
import com.erichydev.nyumbakumi.homeComposables.filters.PriceFilter
import com.erichydev.nyumbakumi.homeComposables.filters.UnitType
import com.erichydev.nyumbakumi.signInComposables.AnimatedButton

@Composable
fun FilterModalSheet(
    viewModel: HomeViewModel,
    filterPlots: () -> Unit,
) {
    val originalLocation = viewModel.selectedLocationOption
    LaunchedEffect(Unit) {
        viewModel.setOriginalLocationOption(originalLocation.value!!)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            FilterTitle()
        }

        item {
            PriceFilter(viewModel)
        }

        item {
            PlotLocation(viewModel)
        }

        item {
            UnitType(viewModel)
        }

        item {
            ApplyFilter(viewModel, filterPlots)
        }
    }
}