package com.uzpsb.nearbyrestaurantsapplication.model.Restaurant


data class Restaurant(
    val id: String,
    val name: String,
    val geoCodes: GeoCodes,
    val distance:Int,
    val location: RestaurantLocation
)

