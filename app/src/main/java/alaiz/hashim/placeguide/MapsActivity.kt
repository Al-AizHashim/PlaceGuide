package alaiz.hashim.placeguide

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



private const val PERMISSION_REQUEST = 10

class MapsActivity : AppCompatActivity() , OnMapReadyCallback{
    private lateinit var map: GoogleMap
    lateinit var placeslist: List<Place>
    var placeCategory:Int=0
    lateinit var deviceLocation:Location
    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private  lateinit var locationGps: Location
    private  lateinit var locationNetwork: Location

    private var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val placeViewModel: PlaceViewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val intent:Intent=getIntent()
        placeCategory= intent.getIntExtra("PlaceCategory",1)


    }


    override fun onStart() {
        super.onStart()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var placeLatLng:LatLng
        map = googleMap
        if (placeCategory==4){
            getDeviceLocation()
            placeLatLng = LatLng(deviceLocation.latitude, deviceLocation.longitude)
            map.addMarker(
                MarkerOptions().position(placeLatLng).title("You are here")
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng, 15.0F))
        }else {
            placeViewModel.loadPlaces(placeCategory)
            placeViewModel.placesLiveData?.observe(this,
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
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng, 14.0F))

                        }
                    }
                }
            )

        }
        }

    fun getDeviceLocation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {
                permissionEnabled()
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        } else {
            permissionEnabled()
        }
    }
    private fun permissionEnabled() {
       deviceLocation= getLocation()
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() : Location {
        var yourDeviceLocation:Location = Location(LocationManager.GPS_PROVIDER)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {

            if (hasGps) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object : LocationListener {


                        override fun onLocationChanged(location: Location) {
                            if (location != null) {
                                locationGps = location
                                yourDeviceLocation=location

                            }
                        }

                        override fun onStatusChanged(provider: String?, status: Int,extras: Bundle?) {
                        }
                    })

                val localGpsLocation =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null)
                    locationGps = localGpsLocation
            }
            if (hasNetwork) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1F, object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            if (location != null) {
                                locationNetwork = location
                                yourDeviceLocation=location
                            }
                        }

                        override fun onStatusChanged(provider: String?,status: Int,extras: Bundle?) {

                        }


                    })

                val localNetworkLocation =
                    locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (localNetworkLocation != null)
                    locationNetwork = localNetworkLocation
            }

            if (locationGps != null && locationNetwork != null) {
                if (locationGps!!.accuracy > locationNetwork!!.accuracy) {
                    yourDeviceLocation=locationGps

                } else {
                    yourDeviceLocation=locationNetwork
                }
            }

        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        return yourDeviceLocation
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain =
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                            permissions[i]
                        )
                    if (requestAgain) {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Go to settings and enable the permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (allSuccess)
                getDeviceLocation()

        }
    }



}