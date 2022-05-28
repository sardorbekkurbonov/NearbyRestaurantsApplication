package com.uzpsb.nearbyrestaurantsapplication.model.Restaurant

import com.uzpsb.nearbyrestaurantsapplication.model.Location.Location

data class RestaurantPosition(    val southwest: Location,
                                  val northeast: Location,
                                  val center: Location,
                                  val radius: Float) {

    fun contains(region: RestaurantPosition): Boolean {
        return contains(region.southwest) && contains(region.northeast)
    }

    fun contains(point: Location): Boolean {
        return point.latitude in southwest.latitude..northeast.latitude
                && containsLongitude(point.longitude)
    }

    private fun containsLongitude(pointLongitude: Double): Boolean {
        val southwestLongitude = southwest.longitude
        val northeastLongitude = northeast.longitude

        return if (southwestLongitude <= northeastLongitude) {
            pointLongitude in southwestLongitude..northeastLongitude
        } else {
            southwestLongitude <= pointLongitude || pointLongitude <= northeastLongitude
        }
    }
}