package com.gutotech.locationinformation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        view.setOnTouchListener { _, event ->
            return@setOnTouchListener when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    scrollView.requestDisallowInterceptTouchEvent(true)
                    false
                }
                MotionEvent.ACTION_UP -> {
                    scrollView.requestDisallowInterceptTouchEvent(false)
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    scrollView.requestDisallowInterceptTouchEvent(true)
                    false
                }
                else -> true
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            return
        }

        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.isMyLocationEnabled = true

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        if (lastKnownLocation != null) {
            showLocationInfo(lastKnownLocation)
            setMarker(lastKnownLocation)
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 0, 0f,
            object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    showLocationInfo(location)
                    setMarker(location)
                }

                override fun onProviderEnabled(provider: String) {
                    Toast.makeText(
                        applicationContext,
                        "Your ${provider.toUpperCase(Locale.ROOT)} is enabled",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onProviderDisabled(provider: String) {
                    Toast.makeText(
                        applicationContext,
                        "Your ${provider.toUpperCase(Locale.ROOT)} is disabled, the app will not work as expected",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun setMarker(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)

        mMap.clear()
        mMap.addMarker(MarkerOptions().position(latLng).title("Your location"))
    }

    private fun showLocationInfo(location: Location) {
        val address = getAddress(location)

        if (address != null) {
            addressTextView.text = "Address: "

            for (index in 0..address.maxAddressLineIndex) {
                val line = address.getAddressLine(index)
                addressTextView.append("$line\n")
            }

            addressTextView.append("Feature Name: ${address.featureName}\n")
            addressTextView.append("Admin Area: ${address.adminArea}\n")
            addressTextView.append("Sub Admin Area: ${address.subAdminArea}\n")

            if (!address.locality.isNullOrEmpty()) {
                addressTextView.append("Locality: ${address.locality}\n")
            }

            if (!address.subLocality.isNullOrEmpty()) {
                addressTextView.append("Sub Locality: ${address.subLocality}\n")
            }

            if (!address.thoroughfare.isNullOrEmpty()) {
                addressTextView.append("Thoroughfare: ${address.thoroughfare}\n")
            }

            if (!address.subThoroughfare.isNullOrEmpty()) {
                addressTextView.append("Sub Thoroughfare: ${address.subThoroughfare}\n")
            }

            if (!address.premises.isNullOrEmpty()) {
                addressTextView.append("Premises: ${address.premises}\n")
            }

            if (!address.postalCode.isNullOrEmpty()) {
                addressTextView.append("Postal Code: ${address.postalCode}\n")
            }

            if (!address.countryCode.isNullOrEmpty()) {
                addressTextView.append("Country Code: ${address.countryCode}\n")
            }

            if (!address.countryName.isNullOrEmpty()) {
                addressTextView.append("Country Name: ${address.countryName}\n")
            }

            addressTextView.append("Latitude: ${address.latitude}\n")
            addressTextView.append("Longitude: ${address.longitude}\n")
            addressTextView.append("Altitude: ${location.altitude}\n")
            addressTextView.append("Accuracy: ${location.accuracy}\n")

            if (!address.phone.isNullOrEmpty()) {
                addressTextView.append("Phone: ${address.phone}\n")
            }

            if (!address.url.isNullOrEmpty()) {
                addressTextView.append("URL: ${address.url}\n")
            }
        } else {
            addressTextView.text = "Could not find address :("
        }
    }

    private fun getAddress(location: Location): Address? {
        val geocoder = Geocoder(this)

        var addresses = ArrayList<Address>()

        try {
            addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            ) as ArrayList<Address>
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return if (addresses.isNotEmpty()) addresses[0] else null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION &&
            grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            addressTextView.text =
                "The app requires location access permission to behave as expected."
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 7
    }
}