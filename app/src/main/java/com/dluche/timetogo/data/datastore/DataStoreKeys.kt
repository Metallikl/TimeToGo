package com.dluche.timetogo.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val PREFERENCE_INITIAL_TIME = stringPreferencesKey("initial_time")
    val PREFERENCE_END_TIME = stringPreferencesKey("end_time")
    val PREFERENCE_LUNCH_INTERVAL = stringPreferencesKey("lunch_interval")
    val PREFERENCE_WORK_TIME = stringPreferencesKey("work_time")
}