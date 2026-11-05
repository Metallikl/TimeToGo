package com.dluche.timetogo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dluche.timetogo.data.datastore.settingsDataStore
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.dluche.timetogo.data")
class DataStoreModule {

    @Single
    fun provideSettingsDataStore(context: Context): DataStore<Preferences> {
        return context.settingsDataStore
    }
}