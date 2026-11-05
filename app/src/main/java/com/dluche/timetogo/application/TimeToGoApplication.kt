package com.dluche.timetogo.application

import android.app.Application
import com.dluche.timetogo.di.DataStoreModule
import com.dluche.timetogo.di.TimeCounterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinApplication
import org.koin.core.context.startKoin
import org.koin.plugin.module.dsl.modules

@KoinApplication
class TimeToGoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TimeToGoApplication)
            modules(
                TimeCounterModule::class,
                DataStoreModule::class,
            )
        }
    }
}