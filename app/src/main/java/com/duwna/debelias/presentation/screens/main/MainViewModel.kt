package com.duwna.debelias.presentation.screens.main

import androidx.lifecycle.ViewModel
import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.data.ResourceManager
import com.duwna.debelias.data.repositories.GroupsRepository
import com.duwna.debelias.data.repositories.SettingsRepository
import com.duwna.debelias.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val groupsRepository: GroupsRepository,
    private val navigator: Navigator,
    private val messageHandler: MessageHandler,
    private val resourceManager: ResourceManager
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    val state = _state.asStateFlow()

}
