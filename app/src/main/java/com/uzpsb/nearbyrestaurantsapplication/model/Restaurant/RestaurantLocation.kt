package com.uzpsb.nearbyrestaurantsapplication.model.Restaurant

data class RestaurantLocation(
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val state: String,
    val country: String,
    val formattedAddress:String
) {

}