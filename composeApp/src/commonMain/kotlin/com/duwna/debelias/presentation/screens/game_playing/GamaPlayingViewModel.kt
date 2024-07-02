package com.duwna.debelias.presentation.screens.game_playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.data.exceptionHandler
import com.duwna.debelias.data.repositories.SettingsRepository
import com.duwna.debelias.data.repositories.WordsRepository
import com.duwna.debelias.navigation.Navigator
import com.duwna.debelias.presentation.screens.game_playing.composables.WordSwipeDirection
import debelias_multiplatform.composeapp.generated.resources.Res
import debelias_multiplatform.composeapp.generated.resources.seconds_template
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

class GamaPlayingViewModel(
    private val settingsRepository: SettingsRepository,
    private val navigator: Navigator,
    private val wordsRepository: WordsRepository,
    private val messageHandler: MessageHandler,
) : ViewModel() {

    private val _state = MutableStateFlow<GamePlayingViewState?>(null)
    val state = _state.asStateFlow()


    init {
        setInitialState()
    }

    fun onWordSwiped(direction: WordSwipeDirection) {
        if (direction == WordSwipeDirection.CENTER) return

        viewModelScope.launch {
            val settings = settingsRepository.observeSettings().first()

            _state.update {
                it?.copy(
                    currentWord = wordsRepository.loadNewWord(),
                    currentPoints = when (direction) {
                        WordSwipeDirection.UP -> it.currentPoints + settings.successWordPoints
                        WordSwipeDirection.DOWN -> it.currentPoints - settings.failureWordPoints
                        WordSwipeDirection.CENTER -> error("illegal state")
                    }
                )
            }
        }
    }

    private fun setInitialState() {
        viewModelScope.launch((exceptionHandler(messageHandler))) {
            val maxSeconds = settingsRepository.observeSettings().first().roundSeconds

            _state.value = GamePlayingViewState(
                currentWord = wordsRepository.loadNewWord(),
                secondsString = getString(Res.string.seconds_template, maxSeconds),
                currentPoints = 0
            )

            createTimer(maxSeconds)
        }
    }

    private fun createTimer(maxSeconds: Int) {
        (maxSeconds - 1 downTo 0).asFlow()
            .onEach { delay(1000) }
            .onEach { secondsLeft ->
                _state.update {
                    it?.copy(
                        secondsString = getString(
                            Res.string.seconds_template,
                            secondsLeft
                        )
                    )
                }
                if (secondsLeft == 0) {
                    wordsRepository.addedPointsFlow.tryEmit(state.value?.currentPoints ?: 0)
                    navigator.popBackStack()
                }
            }
            .launchIn(viewModelScope)
    }
}
