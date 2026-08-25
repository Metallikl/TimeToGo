package com.dluche.timetogo.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dluche.timetogo.navigation.routes.TimeToGoRoutes
import com.dluche.timetogo.ui.feature.timecounter.screen.TimeCounterRoute

@ExperimentalMaterial3Api
@Composable
fun TimeToGoAppNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = TimeToGoRoutes.TimeCounterHome) {
        composable<TimeToGoRoutes.TimeCounterHome>{
            TimeCounterRoute()
        }
    }
}