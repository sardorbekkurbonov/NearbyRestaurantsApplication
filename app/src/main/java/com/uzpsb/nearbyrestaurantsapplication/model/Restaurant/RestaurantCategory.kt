package com.uzpsb.nearbyrestaurantsapplication.model.Restaurant

import com.uzpsb.nearbyrestaurantsapplication.retrofit.model.RestaurantCategoryNetworkEntity

enum class RestaurantCategory {
    FOOD
}


internal fun RestaurantCategory.toNetworkCategory(): RestaurantCategoryNetworkEntity = when (this) {
    RestaurantCategory.FOOD -> RestaurantCategoryNetworkEntity.FOOD
}