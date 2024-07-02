package com.duwna.debelias.di

import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.data.PersistentStorage
import com.duwna.debelias.data.repositories.GroupsRepository
import com.duwna.debelias.data.repositories.SettingsRepository
import com.duwna.debelias.data.repositories.WordsRepository
import com.duwna.debelias.navigation.Navigator
import com.duwna.debelias.navigation.NavigatorImpl
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.duwna.debelias.presentation.screens.game_playing.GamaPlayingViewModel
import com.duwna.debelias.presentation.screens.main.MainViewModel
import com.duwna.debelias.presentation.screens.round_result.RoundResultViewModel
import com.duwna.debelias.presentation.screens.settings.SettingsViewModel

val sharedModule = module {
    singleOf(::PersistentStorage)
    singleOf(::MessageHandler)
    singleOf(::NavigatorImpl).bind<Navigator>()
    singleOf(::SettingsRepository)
    singleOf(::GroupsRepository)
    singleOf(::WordsRepository)

    viewModelOf(::MainViewModel)
    viewModelOf(::GamaPlayingViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::RoundResultViewModel)
}

expect val platformModule: Module