package alaiz.hashim.placeguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity() , OnMapReadyCallback{
    private lateinit var map: GoogleMap
    lateinit var placeslist: List<Place>

    private val placeViewModel: PlaceViewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)


    }


    override fun onStart() {
        super.onStart()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var placeLatLng:LatLng
        map = googleMap

        placeViewModel.placeHospitalListLiveData.observe(this,
            androidx.lifecycle.Observer { places ->
                Log.d("placeListLiveData", places.toString())
                places?.let {
                    placeslist = it
                    for (place in placeslist) {
                         placeLatLng = LatLng(place.latitude, place.longitude)
                        map.addMarker(
                            MarkerOptions().position(placeLatLng).title(place.name)
                                .snippet(place.address)
                        )
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng, 10.0F))

                    }
                }
            }
        )


        }





}