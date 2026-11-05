package com.dluche.timetogo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dluche.timetogo.data.client.KtorClient
import com.dluche.timetogo.data.datastore.settingsDataStore
import io.ktor.client.HttpClient
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

    @Single
    fun provideHttpClient(context: Context): HttpClient {
        return KtorClient.openStreetMapClient(context)
    }
}
