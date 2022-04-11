package com.example.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.views.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

//        val localizations = RealmLocalizationRepository().getLocalizations()
//
//        for (loc in localizations) {
//            val local = LatLng(loc.latitude,loc.longitude)
//            googleMap.addMarker(MarkerOptions().position(local).title(loc.description))
//        }

        val camera = LatLng(49.26965144019531, 19.98034570329297)
        val sydne1y = LatLng(50.05896350982605, 19.9298800941726)
        googleMap.addMarker(MarkerOptions().position(sydne1y).title("E-pamir"))
        googleMap.addMarker(MarkerOptions().position(camera).title("Mountain Explorers"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(camera))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}