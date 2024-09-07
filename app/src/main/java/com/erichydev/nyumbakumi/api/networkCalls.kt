package com.erichydev.nyumbakumi.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import com.erichydev.nyumbakumi.data.FailedRequest
import com.erichydev.nyumbakumi.data.OutreachPlatformsResponse
import com.erichydev.nyumbakumi.data.Plot
import com.erichydev.nyumbakumi.data.PlotCaretakerResponse
import com.erichydev.nyumbakumi.data.PlotPicResponse
import com.erichydev.nyumbakumi.data.PlotRateReview
import com.erichydev.nyumbakumi.data.PlotResponse
import com.erichydev.nyumbakumi.data.PlotsResponse
import com.erichydev.nyumbakumi.data.PromoCodeResponse
import com.erichydev.nyumbakumi.data.UserData
import com.erichydev.nyumbakumi.data.UserRateReview
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.io.IOException

interface ApiService {
    @GET("plots/address/{plot_address}/")
    fun getPlotsByAddress(@Path("plot_address") plot_address: String): Call<PlotsResponse>

    @GET("plots/plot/{plot_number}/")
    fun getPlot(@Path("plot_number") plot_number: String): Call<PlotResponse>

    @GET("plots/{plot_number}/pics/")
    fun getPlotPics(@Path("plot_number") plot_number: String): Call<PlotPicResponse>

    @GET("plots/{plot_number}/caretakers/")
    fun getPlotCaretakers(@Path("plot_number") plot_number: String): Call<PlotCaretakerResponse>

    @FormUrlEncoded
    @POST("plots/set_outreach_platform/")
    fun setOutreachPlatform(@Field("platform_name") platformName: String): Call<OutreachPlatformsResponse>

    @FormUrlEncoded
    @POST("plots/add_or_get_user_data/")
    fun addOrGetUserData(
        @Field("user_name") userName: String,
        @Field("user_email") userEmail: String,
        @Field("subscription") subscription: String?,
        @Field("views") views: Int?,
        @Field("user_phone") userPhone: Int?,
    ): Call<UserData>

    @FormUrlEncoded
    @POST("plots/promote_user/")
    fun promoteUser(
        @Field("code") code: String,
        @Field("user_email") userEmail: String
    ): Call<PromoCodeResponse>

    @GET("plots/get_plot_reviews/{plot_number}/")
    fun getPlotReviews(@Path("plot_number") plot_number: String): Call<PlotRateReview>

    @FormUrlEncoded
    @POST("plots/review_plot/")
    fun reviewPlot(
        @Field("user_email") userEmail: String,
        @Field("user_name") userName: String,
        @Field("rating") rating: Int,
        @Field("review") review: String,
        @Field("plot_number") plotNumber: String,
    ): Call<UserRateReview>
}

val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
val httpClient = OkHttpClient.Builder().addInterceptor(logging)

val gson = GsonBuilder()
    .setLenient()
    .create()
val retrofit = Retrofit.Builder()
    .baseUrl("https://prod-api.nyumbakumi.net/NJqLrVDgyo34WlZSbS8Y/")
    .client(httpClient.build())
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

val apiService = retrofit.create(ApiService::class.java)

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

var job: Job? = null
val failedRequestLiveData = MutableLiveData<FailedRequest?>()
fun getPlotsByAddress(context: Context, plotAddress: String, onSuccess: (List<Plot>) -> Unit, onFailure: (String) -> Unit) {
    job?.cancel()
    job = CoroutineScope(Dispatchers.IO).launch {
        var retry = true
        while (retry) {
            println("Fetching plots by address")
            try {
                if (!isNetworkAvailable(context)) {
                    onFailure("No internet connection")
                    throw Exception("No internet connection")
                } else {
                    apiService.getPlotsByAddress(plotAddress).enqueue(object: Callback<PlotsResponse> {
                        override fun onResponse(call: Call<PlotsResponse>, response: retrofit2.Response<PlotsResponse>) {
                            if (response.isSuccessful) {
                                response.body()?.plots?.let {
                                    onSuccess(it)
                                }
                                retry = false
                            } else {
                                onSuccess(emptyList())
                            }
                        }

                        override fun onFailure(call: Call<PlotsResponse>, t: Throwable) {
                            onSuccess(emptyList())
                            t.printStackTrace()
                        }
                    })
                }
            } catch (e: Exception) {
                onSuccess(emptyList())
                e.printStackTrace()
            }
            delay(5000)
        }
    }
}

fun extrapollateT(t: Throwable) {
    when (t) {
        is HttpException -> {
            failedRequestLiveData.postValue(FailedRequest("Server error"))
        }
        is IOException -> {
            failedRequestLiveData.postValue(FailedRequest("No internet connection"))
        }
        else -> {
            failedRequestLiveData.postValue(FailedRequest("Unknown error"))
        }
    }
}

var outreachPlatformJob: Job? = null
fun outreachPlatform(
    context: Context,
    platformName: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    outreachPlatformJob?.cancel()
    outreachPlatformJob = CoroutineScope(Dispatchers.IO).launch {
        var retry = true
        while (retry) {
            try {
                if (!isNetworkAvailable(context)) {
                    onFailure("No internet connection")
                    throw Exception("No internet connection")
                } else {
                    apiService.setOutreachPlatform(platformName).enqueue(object : Callback<OutreachPlatformsResponse> {
                        override fun onResponse(call: Call<OutreachPlatformsResponse>, response: retrofit2.Response<OutreachPlatformsResponse>) {
                            // do nothing
                            onSuccess()
                            retry = false
                            println("successful platform")
                        }

                        override fun onFailure(call: Call<OutreachPlatformsResponse>, t: Throwable) {
                            onFailure("Failed to set platform")
                            t.printStackTrace()
                        }
                    })
                }
            } catch (e: Exception) {
                onFailure("Failed to set platform")
                e.printStackTrace()
            }
            delay(5000)
        }
    }
}

var userDataJob: Job? = null
fun addOrGetUserData(
    context: Context,
    userName: String,
    userEmail: String,
    subscription: String?,
    views: Int?,
    userPhone: Int?,
    onSuccess: (UserData) -> Unit,
    onFailure: (String) -> Unit
) {
    userDataJob?.cancel()
    userDataJob = CoroutineScope(Dispatchers.IO).launch {
        var retry = true
        while (retry) {
            try {
                if (!isNetworkAvailable(context)) {
                    onFailure("No internet connection")
                    throw Exception("No internet connection")
                } else {
                    apiService.addOrGetUserData(userName, userEmail, subscription, views, userPhone).enqueue(object : Callback<UserData> {
                        override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                            if (response.isSuccessful) {
                                response.body()?.let {
                                    onSuccess(it)
                                }
                                retry = false
                                println("successful user data operation")
                            } else {
                                onFailure("Failed to add or get user data: ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<UserData>, t: Throwable) {
                            onFailure("Failed to add or get user data")
                            t.printStackTrace()
                        }
                    })
                }
            } catch (e: Exception) {
                onFailure("Failed to add or get user data")
                e.printStackTrace()
            }
            delay(5000)
        }
    }
}


fun promoteUser(
    context: Context,
    code: String,
    userEmail: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    try {
        if(!isNetworkAvailable(context)) {
            onFailure("No internet connection")
            throw Exception("Non internet connection")
        } else {
            apiService.promoteUser(code, userEmail).enqueue(object: Callback<PromoCodeResponse> {
                override fun onResponse(
                    call: Call<PromoCodeResponse>,
                    response: Response<PromoCodeResponse>
                ) {
                    if(response.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure("Failed to promote user: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<PromoCodeResponse>, t: Throwable) {
                    onFailure("Failed to promote user")
                    t.printStackTrace()
                }
            })
        }
    } catch (e: Exception) {
        onFailure("Failed to promote user")
        e.printStackTrace()
    }
}