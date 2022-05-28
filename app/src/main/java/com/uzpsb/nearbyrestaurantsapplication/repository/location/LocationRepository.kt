package com.uzpsb.nearbyrestaurantsapplication.repository.location

import com.uzpsb.nearbyrestaurantsapplication.model.Location.Location
import com.uzpsb.nearbyrestaurantsapplication.model.Location.LocationObservationType
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun observeCurrentLocation(type: LocationObservationType): Flow<Location>
}