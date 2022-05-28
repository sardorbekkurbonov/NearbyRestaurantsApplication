package com.uzpsb.nearbyrestaurantsapplication.retrofit

import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.Restaurant
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.RestaurantLocation
import com.uzpsb.nearbyrestaurantsapplication.retrofit.model.RestaurantLocationNetworkEntity
import com.uzpsb.nearbyrestaurantsapplication.retrofit.model.RestaurantNetworkEntity
import com.uzpsb.nearbyrestaurantsapplication.util.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor():
    EntityMapper<RestaurantNetworkEntity, Restaurant> {

    override fun mapFromEntity(entity: RestaurantNetworkEntity): Restaurant {
        return Restaurant(
            id = entity.id?:"",
            name = entity.name?:"",
            geoCodes = entity.geocodes,
            distance =entity.distance,
            location = RestaurantLocation(
                entity.location?.address?:"",
                entity.geocodes.location.latitude,
                entity.geocodes.location.longitude,
                entity.location?.city?:"",
                entity.location?.state?:"",
                entity.location?.country?:"",
                entity.location?.formattedAddress?:""
            )
        )
    }

    override fun mapToEntity(domainModel: Restaurant): RestaurantNetworkEntity {
        return RestaurantNetworkEntity(
            id = domainModel.id,
            name = domainModel.name,
            geocodes = domainModel.geoCodes,
            distance=domainModel.distance,
            location = RestaurantLocationNetworkEntity(domainModel.location.address,
                domainModel.geoCodes.location.latitude,
                domainModel.geoCodes.location.longitude,
                domainModel.location.city,
                domainModel.location.state,
                domainModel.location.country,
                domainModel.location.formattedAddress)

        )
    }



    fun mapFromEntityList(entities: List<RestaurantNetworkEntity>): List<Restaurant>{
        return entities.map { mapFromEntity(it) }
    }
}
