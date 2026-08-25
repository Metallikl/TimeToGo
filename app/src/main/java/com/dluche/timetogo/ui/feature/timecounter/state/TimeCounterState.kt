package com.dluche.timetogo.ui.feature.timecounter.state

data class TimeCounterState(
    val initialTime: String,
    val endTime: String,
    val lunchInterval: String,
    val workTime: String,
    val calculatedLeaveTime: String,
    val realWorkedTime: String,
)
