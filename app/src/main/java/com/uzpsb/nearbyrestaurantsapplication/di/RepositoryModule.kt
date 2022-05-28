package com.uzpsb.nearbyrestaurantsapplication.di

import com.uzpsb.nearbyrestaurantsapplication.repository.location.LocationRepository
import com.uzpsb.nearbyrestaurantsapplication.repository.location.LocationRepositoryImp
import com.uzpsb.nearbyrestaurantsapplication.repository.Restaurant.RestaurantRepository
import com.uzpsb.nearbyrestaurantsapplication.repository.Restaurant.RestaurantRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(repository: LocationRepositoryImp): LocationRepository

    @Binds
    @Singleton
    abstract fun bindVenueRepository(repository: RestaurantRepositoryImp): RestaurantRepository

}