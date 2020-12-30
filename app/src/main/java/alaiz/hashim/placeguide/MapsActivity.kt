package alaiz.hashim.placeguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    var placeslist: List<Place> = emptyList()
    private lateinit var map: GoogleMap
    private val placeViewModel: PlaceViewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        placeViewModel.placeListLiveData.observe(this,
            androidx.lifecycle.Observer { places ->
                places?.let {
                    placeslist = places
                }
            }
        )
    }



    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        for (place in placeslist) {
            val placeLatLng = LatLng(place.latitude, place.longitude)
            map.addMarker(MarkerOptions().position(placeLatLng).title(place.name).snippet(place.address))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng,10.0F))
        }
    }
}