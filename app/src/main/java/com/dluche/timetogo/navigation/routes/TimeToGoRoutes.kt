package com.dluche.timetogo.navigation.routes

import kotlinx.serialization.Serializable

sealed interface TimeToGoRoutes{
    @Serializable
    data object TimeCounterHome: TimeToGoRoutes
}