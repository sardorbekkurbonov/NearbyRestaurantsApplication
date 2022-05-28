package com.uzpsb.nearbyrestaurantsapplication.retrofit.model

import com.google.gson.annotations.SerializedName

data class RestaurantResponse( @SerializedName("results") val restaurantLocations:ArrayList<RestaurantNetworkEntity>)