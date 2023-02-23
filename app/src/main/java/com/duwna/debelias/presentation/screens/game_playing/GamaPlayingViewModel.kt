package com.duwna.debelias.presentation.screens.game_playing

import android.os.Vibrator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duwna.debelias.R
import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.data.ResourceManager
import com.duwna.debelias.data.exceptionHandler
import com.duwna.debelias.data.repositories.GroupsRepository
import com.duwna.debelias.data.repositories.SettingsRepository
import com.duwna.debelias.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamaPlayingViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val groupsRepository: GroupsRepository,
    private val vibrator: Vibrator,
    private val navigator: Navigator,
    private val resourceManager: ResourceManager,
    private val messageHandler: MessageHandler
) : ViewModel() {

    private val _state = MutableStateFlow(GamePlayingViewState())
    val state = _state.asStateFlow()

    fun showNewWord() {
        viewModelScope.launch((exceptionHandler(messageHandler)) + Dispatchers.IO) {
            val newWord = resourceManager.getRawStream(R.raw.all_words_file)
                .use { String(it.readBytes()) }
                .split("\n")
                .random()

            _state.update { it.copy(currentWord = newWord) }
        }
    }

}
