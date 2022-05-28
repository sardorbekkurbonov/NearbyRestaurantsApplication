package com.uzpsb.nearbyrestaurantsapplication.model.Restaurant

import com.google.gson.annotations.SerializedName
import com.uzpsb.nearbyrestaurantsapplication.model.Location.Location

data class GeoCodes(@SerializedName("main") val location:Location) {
}