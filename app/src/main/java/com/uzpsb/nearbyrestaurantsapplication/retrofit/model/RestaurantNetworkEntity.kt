package com.uzpsb.nearbyrestaurantsapplication.retrofit.model

import com.google.gson.annotations.SerializedName
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.GeoCodes

data class RestaurantNetworkEntity(
    @SerializedName("fsq_id") val id:String?,
    @SerializedName("name") val name:String?,
    @SerializedName("geocodes") val geocodes:GeoCodes,
    @SerializedName("distance") val distance:Int,
    @SerializedName("location") val location:RestaurantLocationNetworkEntity?
) {
}