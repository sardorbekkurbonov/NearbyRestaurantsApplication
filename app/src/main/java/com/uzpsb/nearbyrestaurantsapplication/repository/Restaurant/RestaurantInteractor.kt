package com.uzpsb.nearbyrestaurantsapplication.repository.Restaurant

import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.Restaurant
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.RestaurantCategory
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.RestaurantPosition
import javax.inject.Inject

class RestaurantInteractor @Inject constructor(private val restaurantRepository: RestaurantRepository) {

    suspend fun searchRestaurants(restaurantPosition: RestaurantPosition) :List<Restaurant>{
        val restaurantCategory=RestaurantCategory.FOOD

        val cachedRestaurants=restaurantRepository.searchCachedRestaurants(restaurantCategory,restaurantPosition)

        return if(cachedRestaurants.isNotEmpty()){
                cachedRestaurants
        }else{
            restaurantRepository.searchRestaurants(restaurantCategory,restaurantPosition).also{restaurants->
                restaurantRepository.saveRestaurants(restaurantCategory,restaurantPosition,restaurants)
            }
        }
    }
}