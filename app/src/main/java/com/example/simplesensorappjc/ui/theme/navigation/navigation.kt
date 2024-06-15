package com.example.simplesensorappjc.ui.theme.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.simplesensorappjc.R
import com.example.simplesensorappjc.model.MainViewModel
import com.example.simplesensorappjc.ui.theme.screens.GPSScreen
import com.example.simplesensorappjc.ui.theme.screens.GyroScreen
import com.example.simplesensorappjc.ui.theme.screens.HomeScreen

sealed class MyNavDestination(
    val route: String,
    val title: Int = 0,
    val label: Int = 0,
    val selectedIcon: ImageVector = Icons.Default.Check,
    val unselectedIcon: ImageVector = Icons.Default.Check,
    val showArrowBack: Boolean = false,
    val content: @Composable (NavController, MainViewModel) -> Unit
) {

    // hier alle Bildschirme mit den notwendigen Infos dazu listen

    // BottomNavScreens

    object Home : MyNavDestination(
        route = "home",                          // eindeutige Kennung
        title = R.string.homeScreenTitle,        // Titel in der TopBar
        label = R.string.homeScreenLabel,        // Label in der BottomBar
        selectedIcon = Icons.Filled.Home,        // Icon in der BottomBar, wenn gewählt
        unselectedIcon = Icons.Outlined.Home,     // Icon in der BottomBar, wenn nicht gewählt
        // Lambda Funktion, über die der Screen aufgerufen wird
        content = { navController, viewModel -> HomeScreen(navController, viewModel) }
    )

    object Gyro : MyNavDestination(
        route = "gyro",
        title = R.string.gyroScreenTitle,
        label = R.string.gyroScreenLabel,
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
        content = { navController, viewModel -> GyroScreen(navController, viewModel) }
    )

    object GPS : MyNavDestination(
        route = "gps",
        title = R.string.gpsScreenTitle,
        label = R.string.gpsScreenLabel,
        selectedIcon = Icons.Filled.Place,
        unselectedIcon = Icons.Outlined.Place,
        content = { navController, viewModel -> GPSScreen(navController, viewModel) }
    )
}


// Hier alle Bildschirme listen, über die in der Bottom Bar navigiert werden soll
val bottomBarNavDestinations = listOf (
    MyNavDestination.Home,
    MyNavDestination.Gyro,
    MyNavDestination.GPS,
)

val navDestinations = bottomBarNavDestinations