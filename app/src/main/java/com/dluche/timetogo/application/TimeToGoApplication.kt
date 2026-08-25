package com.dluche.timetogo.application

import android.app.Application
import com.dluche.timetogo.ui.feature.timecounter.di.TimeCounterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinApplication
import org.koin.core.context.startKoin
import org.koin.plugin.module.dsl.modules
import org.koin.plugin.module.dsl.startKoin

@KoinApplication
class TimeToGoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TimeToGoApplication)
            modules(
                TimeCounterModule::class
            )
        }
    }
}