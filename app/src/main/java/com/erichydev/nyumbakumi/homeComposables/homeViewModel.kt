package com.erichydev.nyumbakumi.homeComposables

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erichydev.nyumbakumi.api.getPlotsByAddress
import com.erichydev.nyumbakumi.data.Plot

class HomeViewModel : ViewModel() {
    private val _plots = MutableLiveData<List<Plot>>(emptyList())
    val plots: LiveData<List<Plot>> = _plots

    private val _originalPlots = MutableLiveData<List<Plot>>(emptyList())
    val originalPlots: LiveData<List<Plot>> = _originalPlots

    fun setPlots(newPlots: List<Plot>) {
        _plots.postValue(newPlots)
    }
    private fun setNewPlots(newPlots: List<Plot>) {
        _plots.postValue(newPlots)
        _originalPlots.postValue(newPlots)
    }

    fun fetchPlotsByAddress(context: Context, address: String) {
        getPlotsByAddress(
            context,
            address,
            onSuccess = { plots -> setNewPlots(plots) },
            onFailure = { _ -> /* Handle error, e.g., show a message */ }
        )
    }

    private val _originalLocationOption = MutableLiveData("Kiganjo")
    val originalLocationOption: LiveData<String> = _originalLocationOption

    fun setOriginalLocationOption(option: String) {
        _originalLocationOption.postValue(option)
    }

    private val _selectedLocationOption = MutableLiveData("Kiganjo")
    val selectedLocationOption: LiveData<String> = _selectedLocationOption

    fun setSelectedLocationOption(option: String) {
        _selectedLocationOption.postValue(option)
    }

    private val _selectedUnitType = MutableLiveData("All")
    val selectedUnitType: LiveData<String> = _selectedUnitType

    fun setSelectedUnitType(option: String) {
        _selectedUnitType.postValue(option)
    }

    private val _minPrice = MutableLiveData(0f)
    val minPrice: LiveData<Float> = _minPrice

    fun setMinPrice(option: Float) {
        _minPrice.postValue(option)
    }

    private val _maxPrice = MutableLiveData(15000f)
    val maxPrice: LiveData<Float> = _maxPrice

    fun setMaxPrice(option: Float) {
        _maxPrice.postValue(option)
    }

    fun priceUnitFilter() {
        var unitPlots = emptyList<Plot>()
        if (selectedUnitType.value != "All"){
            when(selectedUnitType.value) {
                "Single" -> unitPlots = originalPlots.value!!.filter { plot -> plot.plotSingle }
                "Bedsitter" -> unitPlots = originalPlots.value!!.filter { plot -> plot.plotBedsitter }
                "1-Bedroom" -> unitPlots = originalPlots.value!!.filter { plot -> plot.plot1B }
                "2-Bedroom" -> unitPlots = originalPlots.value!!.filter { plot -> plot.plot2B }
            }
        } else {
            unitPlots = originalPlots.value!!
        }

        val unitPricePlots = unitPlots
            .sortedBy { it.plotPrice }
            .filter { plot ->
                plot.plotPrice in minPrice.value!!.toInt()..maxPrice.value!!.toInt()
            }
        setPlots(unitPricePlots)
    }
    fun filterPlots(context: Context) {
        if(originalLocationOption.value != selectedLocationOption.value) {
            fetchPlotsByAddress(context, selectedLocationOption.value!!)
            return
        }

        priceUnitFilter()
    }
}
