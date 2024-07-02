package com.duwna.debelias.di

import com.duwna.debelias.platform.VibrationManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::VibrationManager)
}