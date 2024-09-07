package com.erichydev.nyumbakumi.plotComposables

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erichydev.nyumbakumi.api.createPlotReview
import com.erichydev.nyumbakumi.api.getPlotReviews
import com.erichydev.nyumbakumi.data.UserRateReview

class RateReviewViewModel : ViewModel() {
    private val _plotRateReviews = MutableLiveData<List<UserRateReview>>(emptyList())
    val plotRateReviews: LiveData<List<UserRateReview>> = _plotRateReviews

    fun setPlotRateReviews(reviews: List<UserRateReview>){
        _plotRateReviews.postValue(reviews)
    }
    fun fetchPlotReviews(context:Context, plotNumber: String){
        getPlotReviews(
            context,
            plotNumber,
            onSuccess = {reviews ->
                setPlotRateReviews(reviews)
            },
            onFailure = {_ -> /* probably do something constructive */}
        )
    }

    fun addPlotReview(review: UserRateReview){
        val currentReviews = _plotRateReviews.value.orEmpty()

        // Create a new list by adding the new review to the current list
        val updatedReviews = currentReviews + review

        // Update the LiveData with the new list
        _plotRateReviews.postValue(updatedReviews)
    }

    fun publishReview(context: Context, plotNumber: String, userEmail: String, userName: String, rating: Int, review: String) {
        createPlotReview(
            context,
            plotNumber,
            userEmail,
            userName,
            rating,
            review,
            onSuccess = {
                fetchPlotReviews(context, plotNumber)
            },
            onFailure = {}
        )
    }
}