package com.uzpsb.nearbyrestaurantsapplication.repository.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.uzpsb.nearbyrestaurantsapplication.model.Location.Location
import com.uzpsb.nearbyrestaurantsapplication.model.Location.LocationObservationType
import com.uzpsb.nearbyrestaurantsapplication.util.CONSTANTS
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationRepositoryImp @Inject constructor(@ApplicationContext private val context: Context):
    LocationRepository {

    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val locationIntervalRequest by lazy {
        LocationRequest.create().apply {
            interval = CONSTANTS.LOCATION_UPDATE_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val locationDisplacementRequest by lazy {
        LocationRequest.create().apply {
            smallestDisplacement = CONSTANTS.SMALLEST_DISPLACEMENT
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    @SuppressLint("MissingPermission")
    override fun observeCurrentLocation(type: LocationObservationType): Flow<Location> =

        callbackFlow {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    trySend(
                        Location(p0.lastLocation.latitude, p0.lastLocation.longitude)
                    )
                }
            }

                val request=when (type){
                    LocationObservationType.TIME_INTERVAL->locationIntervalRequest
                    LocationObservationType.DISPLACEMENT->locationDisplacementRequest
                }

                fusedLocationProviderClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
                )

                awaitClose { fusedLocationProviderClient.removeLocationUpdates(locationCallback) }
            }
        }

