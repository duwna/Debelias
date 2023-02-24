package com.duwna.debelias.presentation.screens.round_result

import android.os.Vibrator
import androidx.lifecycle.ViewModel
import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.data.ResourceManager
import com.duwna.debelias.data.repositories.GroupsRepository
import com.duwna.debelias.data.repositories.SettingsRepository
import com.duwna.debelias.navigation.Navigator
import com.duwna.debelias.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RoundResultViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val groupsRepository: GroupsRepository,
    private val vibrator: Vibrator,
    private val navigator: Navigator,
    private val resourceManager: ResourceManager,
    private val messageHandler: MessageHandler
) : ViewModel() {

    private val _state = MutableStateFlow<RoundResultViewState?>(null)
    val state = _state.asStateFlow()

    fun playNextRound() {
        navigator.navigate(Screen.GamePlaying)
    }

}
