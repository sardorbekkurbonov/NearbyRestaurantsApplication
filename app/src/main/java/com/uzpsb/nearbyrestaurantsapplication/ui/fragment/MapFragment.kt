package com.uzpsb.nearbyrestaurantsapplication.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.uzpsb.nearbyrestaurantsapplication.R
import com.uzpsb.nearbyrestaurantsapplication.databinding.FragmentMapBinding
import com.uzpsb.nearbyrestaurantsapplication.databinding.RestaurantInfoBtmSheetDialogBinding
import com.uzpsb.nearbyrestaurantsapplication.model.Location.Location
import com.uzpsb.nearbyrestaurantsapplication.model.Restaurant.Restaurant
import com.uzpsb.nearbyrestaurantsapplication.ui.dialog.RestaurantInfoBottomSheetDialog
import com.uzpsb.nearbyrestaurantsapplication.util.singleObserve
import com.uzpsb.nearbyrestaurantsapplication.viewmodel.RestaurantViewModel

class MapFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var _binding:FragmentMapBinding?=null
    private val binding get() = _binding!!

    private val viewModel: RestaurantViewModel by viewModels()
    private var map: GoogleMap? = null
    private val markers = HashMap<String, Marker>()

    override fun setupViews() {

        (childFragmentManager.findFragmentById(R.id.mapHolder) as? SupportMapFragment)?.getMapAsync(this)

        viewModel.locationValue.singleObserve(viewLifecycleOwner,::initCurrentLocation)
        viewModel.restaurants.observe(viewLifecycleOwner,::showRestaurants)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentMapBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object{
        const val fragmentTag="Mapfragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onMapReady(p0: GoogleMap) {

        this.map = p0.apply {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }

            isMyLocationEnabled = true

            p0.setOnMarkerClickListener(this@MapFragment)
            p0.setOnCameraIdleListener {
                viewModel.onPinChanged(p0.projection.visibleRegion)
            }
            viewModel.getLocationUpdates()

        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        val restaurant =p0.tag as Restaurant

        val restaurantDetailBottomSheetDialog=RestaurantInfoBottomSheetDialog.newInstance(restaurant)
        restaurantDetailBottomSheetDialog.restaurant=restaurant
        restaurantDetailBottomSheetDialog.show(requireActivity().supportFragmentManager,"RestaurantInfoBottomSheetDialog")
        return true
    }

    private fun initCurrentLocation(location:Location){
        val latLon=LatLng(location.latitude,location.longitude)

        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLon,16F))
    }

    private fun showRestaurants(restaurants:List<Restaurant>){


        val newRestaurantIds=HashSet<String>()
        restaurants.forEach { restaurant ->
            newRestaurantIds.add(restaurant.id)
            if(!markers.containsKey(restaurant.id)){
                val markerOptions=MarkerOptions()
                    .title(restaurant.name)
                    .position(LatLng(restaurant.location.latitude,restaurant.location.longitude))

                map?.addMarker(markerOptions)?.also {
                    it.tag=restaurant
                    markers[restaurant.id]=it
                }
            }

        }

        markers.keys.removeAll{ids->
            val needToRemove=!newRestaurantIds.contains(ids)
            if (needToRemove) markers[ids]?.remove()
            needToRemove
        }
    }


}