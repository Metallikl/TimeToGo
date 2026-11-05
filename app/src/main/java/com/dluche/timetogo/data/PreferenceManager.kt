package com.dluche.timetogo.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single


interface PreferenceManager{
    suspend fun saveData(key: Preferences.Key<String>, value: String)
    suspend fun getData(key: Preferences.Key<String>): String?
}

@Single
class PreferenceManagerImpl(
    private val dataStore: DataStore<Preferences>
) : PreferenceManager {
    override suspend fun saveData(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun getData(key: Preferences.Key<String>): String? {
        return dataStore.data.first()[key]
    }
}
