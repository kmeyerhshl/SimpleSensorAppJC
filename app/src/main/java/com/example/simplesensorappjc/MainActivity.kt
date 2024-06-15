package com.example.simplesensorappjc

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.simplesensorappjc.model.MainViewModel
import com.example.simplesensorappjc.ui.theme.SimpleSensorAppJCTheme
import com.example.simplesensorappjc.ui.theme.screens.MyApp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    private var locationRightsGranted = false

    private val fusedLocationClient : FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this) }
    private var locationRequest: LocationRequest? = null
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                locationRightsGranted = isGranted
                if (isGranted) {
                    Log.i(">>>>", "Rechte erteilt, Daten können abgerufen werden")
                    getLastPositionData()
                    startLocationUpdates()
                } else {
                    Log.i(">>>>", "Rechte NICHT erteilt")
                }
            }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationRightsGranted = true
            Log.i(">>>>", "Rechte waren bereits erteilt, Daten können abgerufen werden")
            getLastPositionData()
            startLocationUpdates()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

        locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations){
                    viewModel.setLocation(location)
                    Log.i(">>>>>", "New location: " + location.toString())
                }
            }
        }

        setContent {
            SimpleSensorAppJCTheme {
                MyApp()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastPositionData() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    Log.i(">>>", "Positionsdaten verfügbar: " + location.toString())
                } else {
                    Log.i(">>>", "Positionsdaten NICHT verfügbar")
                }
            }
    }

    override fun onResume() {
        super.onResume()
        if (locationRightsGranted) startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationRequest ?: return
        fusedLocationClient.requestLocationUpdates(locationRequest!!,
            locationCallback,
            Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }
}