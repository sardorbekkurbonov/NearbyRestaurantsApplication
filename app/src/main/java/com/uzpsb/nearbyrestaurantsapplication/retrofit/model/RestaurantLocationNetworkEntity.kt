package com.uzpsb.nearbyrestaurantsapplication.retrofit.model

import com.google.gson.annotations.SerializedName

data class RestaurantLocationNetworkEntity(
    @SerializedName("address") val address:String?,
    @SerializedName("lat") val lat:Double?,
    @SerializedName("lng") val lng:Double?,
    @SerializedName("city") val city:String?,
    @SerializedName("state") val state:String?,
    @SerializedName("country") val country:String?,
    @SerializedName("formattedAddress") val formattedAddress:String?
 ) {
}