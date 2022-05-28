package com.uzpsb.nearbyrestaurantsapplication.repository.location

import com.uzpsb.nearbyrestaurantsapplication.model.Location.Location
import com.uzpsb.nearbyrestaurantsapplication.model.Location.LocationObservationType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationInteractor @Inject constructor( private val locationRepository: LocationRepository) {

    fun observeCurrentLocation(type: LocationObservationType): Flow<Location> =
        locationRepository.observeCurrentLocation(type)
}