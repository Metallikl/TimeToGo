package com.dluche.timetogo.ui.feature.timecounter.events

sealed interface TimeCounterEvents {
    data class UpdateInitialTime(val initialTime: String) : TimeCounterEvents
    data class UpdateEndTime(val endTime: String) : TimeCounterEvents
    data class UpdateLunchInterval(val interval: String) : TimeCounterEvents
    data class UpdateWorkTime(val time: String) : TimeCounterEvents
}