package com.dluche.timetogo.ui.feature.timecounter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dluche.timetogo.data.PreferenceManager
import com.dluche.timetogo.data.datastore.DataStoreKeys.PREFERENCE_LUNCH_INTERVAL
import com.dluche.timetogo.data.datastore.DataStoreKeys.PREFERENCE_WORK_TIME
import com.dluche.timetogo.domain.repository.OpenStreetMapRepository
import com.dluche.timetogo.ui.feature.timecounter.events.TimeCounterEvents
import com.dluche.timetogo.ui.feature.timecounter.state.TimeCounterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel
import java.util.Locale

@KoinViewModel
class TimeCounterViewModel(
    private val preferenceManager: PreferenceManager,
    private val repository: OpenStreetMapRepository
): ViewModel() {
    val TAG = "TimeCounterViewModel"
    init {
        Log.d(TAG, "chamou init': ")
    }
//    private val _state = MutableStateFlow(TimeCounterState(
//        initialTime = "",
//        endTime = "",
//        lunchInterval = "",
//        workTime = "",
//        timeLeft = ""
//    ))
//    val state: StateFlow<TimeCounterState> = _state
    val state: StateFlow<TimeCounterState>
        field = MutableStateFlow(TimeCounterState(
            initialTime = "",
            endTime = "",
            lunchInterval = "",
            workTime = "",
            calculatedLeaveTime = "",
            realWorkedTime = ""
        ))

    fun dispatchEvent(event: TimeCounterEvents){
        when(event) {
            is TimeCounterEvents.UpdateEndTime -> updateEndTime(event.endTime)
            is TimeCounterEvents.UpdateInitialTime -> updateInitialTime(event.initialTime)
            is TimeCounterEvents.UpdateLunchInterval -> updateLunchTime(event.interval)
            is TimeCounterEvents.UpdateWorkTime -> updateWorkTime(event.time)
            TimeCounterEvents.SaveSettings -> saveSettings()
            TimeCounterEvents.LoadSettings -> loadSettings()
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            preferenceManager.getData(PREFERENCE_WORK_TIME)?.let{
                updateWorkTime(it)
            }
            preferenceManager.getData(PREFERENCE_LUNCH_INTERVAL)?.let {
                updateLunchTime(it)
            }

            repository.searchAddress("rua okinawa,santo andré")
        }
    }

    private fun saveSettings() {
        viewModelScope.launch {
            preferenceManager.saveData(PREFERENCE_WORK_TIME, state.value.workTime)
            preferenceManager.saveData(PREFERENCE_LUNCH_INTERVAL, state.value.lunchInterval)
        }
    }

    private fun updateWorkTime(time: String){
        state.update { it.copy(workTime = sanitizeHour(time))}
        recalculateLeaveTime()
    }

    private fun updateLunchTime(interval: String) {
        state.update { it.copy(lunchInterval = sanitizeHour(interval))}
        recalculateLeaveTime()
    }

    private fun updateInitialTime(initialTime: String){
        state.update { it.copy(initialTime = sanitizeHour(initialTime))}
        recalculateLeaveTime()
    }

    private fun updateEndTime(endTime: String){
        state.update { it.copy(endTime = sanitizeHour(endTime))}
        recalculateRealWorkedTime()
    }

    private fun updateCalculatedLeaveTime(calculatedLeaveTime: String){
        state.update { it.copy(calculatedLeaveTime = calculatedLeaveTime)}
    }

    private fun recalculateLeaveTime(){
        if(isCalculateLeaveTimeDataFilled()){
            val initialMillis = timeToMillis(state.value.initialTime)
            val workMillis = timeToMillis(state.value.workTime)
            val lunchMillis = timeToMillis(state.value.lunchInterval)
            
            val totalMillis = initialMillis + workMillis + lunchMillis
            val formattedEndTime = millisToTime(totalMillis)

            updateCalculatedLeaveTime(formattedEndTime)
        } else {
            updateCalculatedLeaveTime("00:00")
        }
    }

    private fun recalculateRealWorkedTime(){
        if(isAllDataFilled()){
            val initialMillis = timeToMillis(state.value.initialTime)
            val workMillis = timeToMillis(state.value.workTime)
            val lunchMillis = timeToMillis(state.value.lunchInterval)
            val endMillis = timeToMillis(state.value.endTime)

            val totalMillis = endMillis - (initialMillis + lunchMillis)
            val formattedEndTime = millisToTime(totalMillis)

            updateRealWorkedTime(formattedEndTime)
        } else {
            updateRealWorkedTime("00:00")
        }
    }

    private fun updateRealWorkedTime(realWorkedTime: String) {
        state.update { it.copy(realWorkedTime = realWorkedTime) }
    }

    private fun isCalculateLeaveTimeDataFilled() = with(state.value) {
        initialTime.isNotBlank() && lunchInterval.isNotBlank() && workTime.isNotBlank()
    }

    private fun isAllDataFilled() = with(state.value) {
        isCalculateLeaveTimeDataFilled() && endTime.isNotBlank()
    }

    private fun timeToMillis(time: String): Long {
        if (time.length < 4) return 0L
        val hours = time.substring(0, 2).toLongOrNull() ?: 0L
        val minutes = time.substring(2, 4).toLongOrNull() ?: 0L
        return (hours * 3600000L) + (minutes * 60000L)
    }

    private fun millisToTime(millis: Long): String {
        val totalMinutes = millis / 60000L
        val hours = (totalMinutes / 60) % 24
        val minutes = totalMinutes % 60
        return String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)
    }
    private fun sanitizeHour(time: String): String {
        val sanitized = time.filter { it.isDigit() }.take(4)
        return sanitized
    }
}