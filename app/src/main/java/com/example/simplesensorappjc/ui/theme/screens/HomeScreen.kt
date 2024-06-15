package com.example.simplesensorappjc.ui.theme.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simplesensorappjc.model.MainViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    var sensorList by remember { mutableStateOf<List<Sensor>>(emptyList()) }

    LaunchedEffect(Unit) {
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(sensorList) { sensor ->
            Text(modifier = Modifier.padding(horizontal = 5.dp), text = sensor.toString(), fontSize = 16.sp)
            Divider()
        }
    }
}