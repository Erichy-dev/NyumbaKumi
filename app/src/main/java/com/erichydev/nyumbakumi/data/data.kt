package com.erichydev.nyumbakumi.data

import androidx.compose.runtime.Immutable
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.User
import com.google.gson.annotations.SerializedName

@Immutable
data class Plot(
    @SerializedName("plot_number") val plotNumber: String,
    @SerializedName("plot_owner") val plotOwner: String,
    @SerializedName("plot_name") val plotName: String,
    @SerializedName("plot_location") val plotLocation: String,
    @SerializedName("plot_address") val plotAddress: String,
    @SerializedName("plot_map") val plotMap: String?,
    @SerializedName("plot_status") val plotStatus: String,
    @SerializedName("plot_price") val plotPrice: Int,
    @SerializedName("plot_price_description") val plotPriceDescription: String?,
    @SerializedName("plot_single") val plotSingle: Boolean,
    @SerializedName("plot_bedsitter") val plotBedsitter: Boolean,
    @SerializedName("plot_1B") val plot1B: Boolean,
    @SerializedName("plot_2B") val plot2B: Boolean,
    @SerializedName("plot_3B") val plot3B: Boolean,
    @SerializedName("plot_type") val plotType: String,
    @SerializedName("plot_electricity") val plotElectricity: String,
    @SerializedName("plot_water") val plotWater: String,
    @SerializedName("plot_garbage") val plotGarbage: String,
    @SerializedName("plot_security") val plotSecurity: String,
    @SerializedName("plot_rating") val plotRating: Int,
    @SerializedName("plot_bg_pic") val plotBgPic: String?,
    @SerializedName("plot_upload_date") val plotUploadDate: String,
)
@Immutable
data class PlotsResponse(
    @SerializedName("plots") val plots: List<Plot>
)
data class PlotResponse (
    @SerializedName("plot") val plot: Plot
)

data class PlotPic(
    @SerializedName("plot_number") val plotNumber: String,
    @SerializedName("plot_pic") val plotPic: String,
    @SerializedName("plot_pic_desc") val plotPicDesc: String?
)
data class PlotPicResponse (
    @SerializedName("plot_pics") val plotPics: List<PlotPic>
)

data class PlotOccupant(
    @SerializedName("plot_number") val plotNumber: String,
    @SerializedName("plot_occupant_id") val plotOccupantId: String,
    @SerializedName("plot_occupant_f_name") val plotOccupantFName: String,
    @SerializedName("plot_occupant_l_name") val plotOccupantLName: String?,
    @SerializedName("plot_occupant_class") val plotOccupantClass: String,
    @SerializedName("plot_occupant_phone") val plotOccupantPhone: String,
    @SerializedName("plot_occupant_email") val plotOccupantEmail: String?
)
data class PlotCaretakerResponse (
    @SerializedName("caretakers") val caretakers: List<PlotOccupant>
)

data class FailedRequest(
    val error: String
)

data class OutreachPlatform(
    @SerializedName("platform_name") val platformName: String,
)
data class OutreachPlatformsResponse (
    @SerializedName("platform") val platform: OutreachPlatform
)

data class UserData(
    @SerializedName("user_name") val userFName: String,
    @SerializedName("user_email") val userEmail: String,
    @SerializedName("user_phone") val userPhone: Int?,
    @SerializedName("subscription") val subscription: String,
    @SerializedName("views") val views: Int,
)

data class PromoCodeResponse (
    @SerializedName("message") val message: String
)

data class PlotInfoFilterSchema(
    val infoFilter: String,
    val svg: String
)

data class UserRateReview(
    @SerializedName("plot_number") val plotNumber: String,
    @SerializedName("user_email") val userEmail: String,
    @SerializedName("user_name") val userName: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("review") val review: String,
)

data class PlotRateReview(
    @SerializedName("reviews") val reviews: List<UserRateReview>
)