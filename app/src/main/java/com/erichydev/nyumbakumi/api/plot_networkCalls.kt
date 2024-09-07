package com.erichydev.nyumbakumi.api

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.erichydev.nyumbakumi.data.FailedRequest
import com.erichydev.nyumbakumi.data.Plot
import com.erichydev.nyumbakumi.data.PlotCaretakerResponse
import com.erichydev.nyumbakumi.data.PlotPicResponse
import com.erichydev.nyumbakumi.data.PlotRateReview
import com.erichydev.nyumbakumi.data.PlotResponse
import com.erichydev.nyumbakumi.data.UserRateReview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val plotLiveData = MutableLiveData<PlotResponse>()
var plotJob: Job? = null
fun getPlot(context: Context, plotId: String) {
    plotJob?.cancel()
    plotJob = CoroutineScope(Dispatchers.IO).launch {
        var retry = true
        while (retry) {
            println("Fetching plot data")
            try {
                if (isNetworkAvailable(context)) {
                    val call = apiService.getPlot(plotId)
                    call.enqueue(object : Callback<PlotResponse> {
                        override fun onResponse(call: Call<PlotResponse>, response: Response<PlotResponse>) {
                            if (response.isSuccessful) {
                                plotLiveData.postValue(response.body())
                                retry = false
                            }
                        }

                        override fun onFailure(call: Call<PlotResponse>, t: Throwable) {
                            extrapollateT(t)
                            t.printStackTrace()
                        }
                    })
                } else {
                    failedRequestLiveData.postValue(FailedRequest("No internet connection"))
                    throw Exception("No internet connection")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            delay(5000)
        }
    }
}

val plotPicLiveData = MutableLiveData<PlotPicResponse>()
var plotPicJob: Job? = null
fun getPlotPics(context: Context, plotId: String) {
    plotPicJob?.cancel()
    plotPicJob = CoroutineScope(Dispatchers.IO).launch {
        var retry = true
        while (retry) {
            println("Fetching plot pics")
            try {
                if (isNetworkAvailable(context)) {
                    val call = apiService.getPlotPics(plotId)
                    call.enqueue(object : Callback<PlotPicResponse> {
                        override fun onResponse(call: Call<PlotPicResponse>, response: Response<PlotPicResponse>) {
                            if (response.isSuccessful) {
                                plotPicLiveData.postValue(response.body())
                                retry = false
                            } else {
                                plotPicLiveData.postValue(PlotPicResponse(emptyList()))
                            }
                        }

                        override fun onFailure(call: Call<PlotPicResponse>, t: Throwable) {
                            extrapollateT(t)
                            t.printStackTrace()
                        }
                    })
                } else {
                    plotPicLiveData.postValue(PlotPicResponse(emptyList()))
                    failedRequestLiveData.postValue(FailedRequest("No internet connection"))
                    throw Exception("No internet connection")
                }
            } catch (e: Exception) {
                plotPicLiveData.postValue(PlotPicResponse(emptyList()))
                e.printStackTrace()
            }
            delay(5000)
        }
    }
}

val plotCaretakersLiveData = MutableLiveData<PlotCaretakerResponse>()
var plotCaretakersJob: Job? = null
fun getPlotCaretakers(context: Context, plotId: String) {
    plotCaretakersJob?.cancel()
    plotCaretakersJob = CoroutineScope(Dispatchers.IO).launch {
        var retry = true
        while (retry) {
            println("Fetching plot caretakers")
            try {
                if (isNetworkAvailable(context)) {
                    val call = apiService.getPlotCaretakers(plotId)
                    call.enqueue(object : Callback<PlotCaretakerResponse> {
                        override fun onResponse(call: Call<PlotCaretakerResponse>, response: Response<PlotCaretakerResponse>) {
                            if (response.isSuccessful) {
                                plotCaretakersLiveData.postValue(response.body())
                                retry = false
                            } else {
                                plotCaretakersLiveData.postValue(PlotCaretakerResponse(emptyList()))
                            }
                        }

                        override fun onFailure(call: Call<PlotCaretakerResponse>, t: Throwable) {
                            plotCaretakersLiveData.postValue(PlotCaretakerResponse(emptyList()))
                            extrapollateT(t)
                            t.printStackTrace()
                        }
                    })
                } else {
                    plotCaretakersLiveData.postValue(PlotCaretakerResponse(emptyList()))
                    failedRequestLiveData.postValue(FailedRequest("No internet connection"))
                    throw Exception("No internet connection")
                }
            } catch (e: Exception) {
                plotCaretakersLiveData.postValue(PlotCaretakerResponse(emptyList()))
                e.printStackTrace()
            }
            delay(5000)
        }
    }
}

fun getPlotData(context: Context, plotId: String) {
    getPlot(context, plotId)
    getPlotPics(context, plotId)
    getPlotCaretakers(context, plotId)
}

var plotRateReviewJob: Job? = null
fun getPlotReviews(context: Context, plotId: String, onSuccess: (List<UserRateReview>) -> Unit, onFailure: (String) -> Unit) {
    plotRateReviewJob?.cancel()
    plotRateReviewJob = CoroutineScope(Dispatchers.IO).launch {
        var retry = true
        while (retry) {
            onFailure("Fetching plot reviews")
            try {
                if (isNetworkAvailable(context)) {
                    val call = apiService.getPlotReviews(plotId)
                    call.enqueue(object : Callback<PlotRateReview> {
                        override fun onResponse(call: Call<PlotRateReview>, response: Response<PlotRateReview>) {
                            if (response.isSuccessful) {
                                response.body()?.reviews?.let {
                                    onSuccess(it)
                                }
                                retry = false
                            } else {
                                onSuccess(emptyList())
                            }
                        }

                        override fun onFailure(call: Call<PlotRateReview>, t: Throwable) {
                            onSuccess(emptyList())
                            extrapollateT(t)
                            t.printStackTrace()
                        }
                    })
                } else {
                    onSuccess(emptyList())
                    failedRequestLiveData.postValue(FailedRequest("No internet connection"))
                    throw Exception("No internet connection")
                }
            } catch (e: Exception) {
                onSuccess(emptyList())
                onFailure(e.message.toString())
            }
            delay(5000)
        }
    }
}

fun createPlotReview(
    context: Context,
    plotNumber: String,
    userEmail: String,
    userName: String,
    rating: Int,
    review: String,
    onSuccess: (UserRateReview) -> Unit,
    onFailure: (String) -> Unit
) {
    try {
        if (isNetworkAvailable(context)) {
            val call = apiService.reviewPlot(userEmail, userName, rating, review, plotNumber)
            call.enqueue(object : Callback<UserRateReview> {
                override fun onResponse(call: Call<UserRateReview>, response: Response<UserRateReview>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess(it)
                        }
                    }
                }

                override fun onFailure(call: Call<UserRateReview>, t: Throwable) {
                    extrapollateT(t)
                    t.printStackTrace()
                }
            })
        } else {
            failedRequestLiveData.postValue(FailedRequest("No internet connection"))
            throw Exception("No internet connection")
        }
    } catch (e: Exception) {
        onFailure(e.message.toString())
    }
}