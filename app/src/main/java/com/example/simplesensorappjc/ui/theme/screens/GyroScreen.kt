package com.example.simplesensorappjc.ui.theme.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simplesensorappjc.R
import com.example.simplesensorappjc.model.MainViewModel

@Composable
fun GyroScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    var gyroX by remember { mutableStateOf(0f) }
    var gyroY by remember { mutableStateOf(0f) }
    var gyroZ by remember { mutableStateOf(0f) }
    var backgroundColor by remember { mutableStateOf(Color.White) }


    val gyroListener = remember {
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Log.i(">>>>", "onAccuracyChanged")
            }

            override fun onSensorChanged(sensorEvent: SensorEvent) {
                gyroX = sensorEvent.values[0]
                gyroY = sensorEvent.values[1]
                gyroZ = sensorEvent.values[2]

                backgroundColor = when {
                    gyroY > 0.5f -> Color.Red
                    gyroY < -0.5f -> Color.Green
                    else -> Color.White
                }
            }
        }
    }

    DisposableEffect(Unit) {
        sensorManager.registerListener(gyroListener, gyro, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose {
            sensorManager.unregisterListener(gyroListener)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (gyro != null) {
            Text(text = stringResource(id = R.string.gyro_res, gyro.resolution), fontSize = 16.sp)
        }
        if (gyro != null) {
            Text(text =stringResource(id = R.string.gyro_range, gyro.maximumRange), fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.gyro_x, gyroX), fontSize = 16.sp)
        Text(text = stringResource(id = R.string.gyro_y, gyroY), fontSize = 16.sp)
        Text(text = stringResource(id = R.string.gyro_z, gyroZ), fontSize = 16.sp)
    }
}
