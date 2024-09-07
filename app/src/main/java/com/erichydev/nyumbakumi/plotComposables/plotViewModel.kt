package com.erichydev.nyumbakumi.plotComposables

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlotViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _plotId = MutableStateFlow<String>("")
    val plotId: StateFlow<String>
        get() = _plotId

    init {
        initializePlot()
    }

    private fun initializePlot() {
        viewModelScope.launch {
            val plotId = savedStateHandle.get<String>("plotId")
            if (plotId.isNullOrEmpty()) {
                return@launch
            }
            _plotId.value = plotId
        }
    }
}