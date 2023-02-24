package com.duwna.debelias.presentation.screens.game_playing

import android.os.CountDownTimer
import android.os.Vibrator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duwna.debelias.R
import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.data.ResourceManager
import com.duwna.debelias.data.exceptionHandler
import com.duwna.debelias.data.repositories.SettingsRepository
import com.duwna.debelias.data.repositories.WordsRepository
import com.duwna.debelias.navigation.Navigator
import com.duwna.debelias.presentation.screens.game_playing.composables.WordSwipeDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class GamaPlayingViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val navigator: Navigator,
    private val resourceManager: ResourceManager,
    private val wordsRepository: WordsRepository,
    private val messageHandler: MessageHandler
) : ViewModel() {

    private val _state = MutableStateFlow<GamePlayingViewState?>(null)
    val state = _state.asStateFlow()

    private var timer: CountDownTimer? = null

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
                secondsString = resourceManager.string(R.string.seconds_template, maxSeconds),
                currentPoints = 0
            )

            createTimer(maxSeconds)
        }
    }

    private fun createTimer(maxSeconds: Int) {
        timer = object : CountDownTimer(maxSeconds * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / 1000.0).roundToInt()
                _state.update {
                    it?.copy(secondsString = resourceManager.string(R.string.seconds_template, secondsLeft))
                }
            }

            override fun onFinish() {
                wordsRepository.addedPointsFlow.tryEmit(state.value?.currentPoints ?: 0)
                navigator.popBackStack()
            }
        }.start()
    }

    override fun onCleared() {
        timer?.cancel()
        super.onCleared()
    }

}
