package com.uzpsb.nearbyrestaurantsapplication.repository.Restaurant

import android.util.Log
import com.uzpsb.nearbyrestaurantsapplication.model.Location.Location
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.*
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.toNetworkCategory
import com.uzpsb.nearbyrestaurantsapplication.retrofit.NetworkMapper
import com.uzpsb.nearbyrestaurantsapplication.retrofit.RestaurantRetrofit
import com.uzpsb.nearbyrestaurantsapplication.util.CONSTANTS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

class RestaurantRepositoryImp @Inject constructor(private val restaurantRetrofit: RestaurantRetrofit,private val networkMapper: NetworkMapper):RestaurantRepository {

    private val cache = HashMap<RestaurantCategory,MutableMap<RestaurantPosition,List<Restaurant>>>()

    override suspend fun searchRestaurants(
        restaurantCategory: RestaurantCategory,
        restaurantPosition: RestaurantPosition
    ): List<Restaurant> = withContext(Dispatchers.IO){
        val restaurantCategoryNetworkEntity=restaurantCategory.toNetworkCategory()
        val latLng=CONSTANTS.LAT_LONG_PATTERN.format(
            restaurantPosition.center.latitude,
            restaurantPosition.center.longitude
        )

        val radius=restaurantPosition.radius.roundToInt()

        restaurantRetrofit.searchVenues(
            restaurantCategoryNetworkEntity.id,
            latLng,radius
        ).let{response->
            Log.d("miqqi", "searchRestaurants: "+response.toString())
            networkMapper.mapFromEntityList(response.restaurantLocations)
        }
    }

    override suspend fun searchCachedRestaurants(
        restaurantCategory: RestaurantCategory,
        restaurantPosition: RestaurantPosition
    ): List<Restaurant> {

        cache[restaurantCategory]?.forEach{(location,restaurant)->
            if(location.contains(restaurantPosition)){
                return restaurant.filterByPosition(restaurantPosition)
            }
        }
        return emptyList()
    }

    override suspend fun saveRestaurants(
        restaurantCategory: RestaurantCategory,
        restaurantPosition: RestaurantPosition,
        restaurants: List<Restaurant>
    ) = withContext(Dispatchers.IO){
        val categoryCache=cache[restaurantCategory]?:HashMap()
        val narrowPositions=categoryCache.keys.filter { position ->restaurantPosition.contains(position)  }

        categoryCache.keys.removeAll(narrowPositions)
        categoryCache[restaurantPosition] =restaurants
        cache[restaurantCategory]=categoryCache
    }

    private fun List<Restaurant>.filterByPosition(restaurantPosition: RestaurantPosition): List<Restaurant> =
        filter { restaurant ->
            restaurantPosition.contains(
                Location(
                    restaurant.location.latitude,
                    restaurant.location.longitude
                )
            )
        }

}