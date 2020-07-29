package com.gutotech.pokemongame

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    private var lastLocation: Location? = null

    private val mPokemons = ArrayList<Pokemon>()
    private var playerPower = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        loadPokemons()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getUserLocation()
    }

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
            return
        }

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 0, 0f, object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if (lastLocation != null && lastLocation!!.distanceTo(location) == 0f) {
                        return
                    }

                    lastLocation = location

                    addMarker(
                        "Me", LatLng(location.latitude, location.longitude),
                        "Here is my location", R.drawable.mario, true
                    )

                    mPokemons.forEach {
                        if (!it.isCatched) {
                            if (location.distanceTo(it.location) < 2) {
                                it.isCatched = true
                                playerPower += it.power!!
                                val toast = Toast.makeText(
                                    applicationContext,
                                    "You got a new pokemon, your new power is $playerPower",
                                    Toast.LENGTH_LONG
                                )
                                toast.setGravity(Gravity.TOP, 0, 16)
                                toast.show()
                            } else {
                                addMarker(
                                    it.name!!,
                                    LatLng(it.location!!.latitude, it.location!!.longitude),
                                    it.description!!,
                                    it.image!!,
                                    false
                                )
                            }
                        }
                    }
                }

                override fun onProviderEnabled(provider: String) {
//                    super.onProviderEnabled(provider)
                    Toast.makeText(applicationContext, "GPS ENABLED", Toast.LENGTH_SHORT).show()
                }

                override fun onProviderDisabled(provider: String) {
//                    super.onProviderDisabled(provider)
                    Toast.makeText(applicationContext, "GPS DISABLED", Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    private fun addMarker(
        title: String, position: LatLng, description: String,
        image: Int, moveCamera: Boolean
    ) {
        mMap.addMarker(
            MarkerOptions()
                .title(title)
                .position(position)
                .snippet(description)
                .icon(BitmapDescriptorFactory.fromResource(image))
        )

        if (moveCamera) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 14f))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    getUserLocation()
                } else {
                    Toast.makeText(
                        this, "We cannot access your location", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun loadPokemons() {
        mPokemons.add(
            Pokemon(
                R.drawable.charmander, "Charmander", "Charmander living in japan",
                55.0, -23.5215051, -46.4783792
            )
        )
        mPokemons.add(
            Pokemon(
                R.drawable.bulbasaur, "Bulbasaur", "Bulbasaur living in usa",
                90.5, -23.523519, -46.4774307
            )
        )
        mPokemons.add(
            Pokemon(
                R.drawable.squirtle,
                "Squirtle", "Squirtle living in iraq",
                33.5, -23.507239, -46.4496482
            )
        )
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }
}