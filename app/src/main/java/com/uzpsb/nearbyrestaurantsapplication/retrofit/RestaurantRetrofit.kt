package com.uzpsb.nearbyrestaurantsapplication.retrofit

import com.uzpsb.nearbyrestaurantsapplication.retrofit.model.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantRetrofit {

    @GET("search")
    suspend fun searchVenues(
        @Query("categoryId") category:String,
        @Query("ll") latLong: String,
        @Query("radius") radius:Int

    ):RestaurantResponse
}