package com.dluche.timetogo.di

import com.dluche.timetogo.util.dispatcher.DefaultDispatcherProvider
import com.dluche.timetogo.util.dispatcher.DispatcherProvider
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.dluche.timetogo.ui.feature.timecounter")
class TimeCounterModule {

    @Single
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}
