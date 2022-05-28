package com.uzpsb.nearbyrestaurantsapplication.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uzpsb.nearbyrestaurantsapplication.databinding.RestaurantInfoBtmSheetDialogBinding
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.Restaurant

class RestaurantInfoBottomSheetDialog: BottomSheetDialogFragment() {

    private var _binding :RestaurantInfoBtmSheetDialogBinding?=null
    private val binding get() = _binding!!

    var restaurant:Restaurant?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RestaurantInfoBtmSheetDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurantName="Name: "+requireArguments().getString(NAME)
        val restaurantAddress="Address: "+requireArguments().getString(ADDRESS)
        val restaurantType="Type: "+requireArguments().getString(TYPE)
        val restaurantDistance="Distance: "+requireArguments().getString(DISTANCE) +" meters"
        binding.tvRestaurantAddress.text=restaurantName
        binding.tvRestaurantTitle.text=restaurantAddress
        binding.tvRestaurantDistance.text=restaurantDistance
        binding.tvRestaurantType.text=restaurantType

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    companion object {

        private const val NAME = "NAME"
        private const val ADDRESS = "ADDRESS"
        private const val DISTANCE= "DISTANCE"
        private const val TYPE= "TYPE"

        fun newInstance(restaurant:Restaurant) = RestaurantInfoBottomSheetDialog().apply {
            arguments = Bundle().apply {
                putString(NAME, restaurant.name)
                putString(ADDRESS, restaurant.location.address)
                putString(DISTANCE, restaurant.distance.toString())
            }
        }

    }
}