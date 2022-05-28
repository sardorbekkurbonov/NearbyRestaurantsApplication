package com.uzpsb.nearbyrestaurantsapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.VisibleRegion
import com.uzpsb.nearbyrestaurantsapplication.model.Location.Location
import com.uzpsb.nearbyrestaurantsapplication.model.Location.LocationObservationType
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.Restaurant
import com.uzpsb.nearbyrestaurantsapplication.repository.location.LocationInteractor
import com.uzpsb.nearbyrestaurantsapplication.repository.Restaurant.RestaurantInteractor
import com.uzpsb.nearbyrestaurantsapplication.util.toRestaurantPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val locationInteractor: LocationInteractor,
    private val restaurantInteractor: RestaurantInteractor
) :ViewModel(){

    private val _locationValue= MutableLiveData<Location>()
    val locationValue: LiveData<Location> = _locationValue

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants:LiveData<List<Restaurant>> = _restaurants

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage :LiveData<String> = _errorMessage

    fun onPinChanged(visibleRegion: VisibleRegion){
         val restaurantPosition=visibleRegion.toRestaurantPosition()
        val restaurantErrorHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
            _errorMessage.value=throwable.message
        }

        viewModelScope.launch(restaurantErrorHandler) {

            val restaurants=restaurantInteractor.searchRestaurants(restaurantPosition)
            _restaurants.value=restaurants
        }
    }

    fun getLocationUpdates(){
        val locationErrorHandler= CoroutineExceptionHandler { coroutineContext, throwable ->

        }
        viewModelScope.launch(locationErrorHandler) {
            locationInteractor.observeCurrentLocation(LocationObservationType.DISPLACEMENT)
                .collect{location-> _locationValue.value=location}
        }
    }

}