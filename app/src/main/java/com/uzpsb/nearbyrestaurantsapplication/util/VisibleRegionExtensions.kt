package com.uzpsb.nearbyrestaurantsapplication.util

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.VisibleRegion
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.RestaurantPosition

internal fun VisibleRegion.toRestaurantPosition() = RestaurantPosition(
    southwest = latLngBounds.southwest.toLocationLatLng(),
    northeast = latLngBounds.northeast.toLocationLatLng(),
    center = latLngBounds.center.toLocationLatLng(),
    radius = getVisibleRadius()
)

internal fun LatLng.toLocationLatLng() = com.uzpsb.nearbyrestaurantsapplication.model.Location.Location(latitude, longitude)

internal fun VisibleRegion.getVisibleRadius(): Float {
    val distanceWidth = FloatArray(1)
    Location.distanceBetween(
        (farLeft.latitude + nearLeft.latitude) / 2,
        farLeft.longitude,
        (farRight.latitude + nearRight.latitude) / 2,
        farRight.longitude,
        distanceWidth
    )
    return distanceWidth[0] / 2F
}