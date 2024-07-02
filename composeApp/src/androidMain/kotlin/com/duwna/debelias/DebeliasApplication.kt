package com.duwna.debelias

import android.app.Application
import com.duwna.debelias.di.initKoin
import org.koin.android.ext.koin.androidContext

class DebeliasApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@DebeliasApplication)
        }
    }
}