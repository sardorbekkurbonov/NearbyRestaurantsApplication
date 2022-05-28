package com.uzpsb.nearbyrestaurantsapplication.repository.Restaurant

import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.Restaurant
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.RestaurantCategory
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.RestaurantPosition

interface RestaurantRepository {

    suspend fun searchRestaurants(restaurantCategory: RestaurantCategory, restaurantPosition: RestaurantPosition ):List<Restaurant>

    suspend fun searchCachedRestaurants(restaurantCategory: RestaurantCategory,restaurantPosition: RestaurantPosition):List<Restaurant>

    suspend fun saveRestaurants(restaurantCategory: RestaurantCategory,restaurantPosition: RestaurantPosition,restaurants:List<Restaurant>)
}