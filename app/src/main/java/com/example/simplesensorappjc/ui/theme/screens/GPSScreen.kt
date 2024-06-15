package com.example.simplesensorappjc.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simplesensorappjc.R
import com.example.simplesensorappjc.model.MainViewModel

@Composable
fun GPSScreen(
    navController: NavController,
    viewModel: MainViewModel
) {


    val context = LocalContext.current

    val location by viewModel.location.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when {
                location == null -> "No position available"
                else -> stringResource(
                    id = R.string.gps_position,
                    location!!.longitude,
                    location!!.latitude,
                    location!!.time
                )
            },
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}