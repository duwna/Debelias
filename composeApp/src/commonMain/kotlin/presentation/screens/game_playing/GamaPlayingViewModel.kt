package presentation.screens.game_playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.MessageHandler
import data.exceptionHandler
import data.repositories.SettingsRepository
import data.repositories.WordsRepository
import debelias_multiplatform.composeapp.generated.resources.Res
import debelias_multiplatform.composeapp.generated.resources.seconds_template
import di.AppModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navigation.Navigator
import org.jetbrains.compose.resources.getString
import presentation.screens.game_playing.composables.WordSwipeDirection

class GamaPlayingViewModel(
    private val settingsRepository: SettingsRepository = AppModule.settingsRepository,
    private val navigator: Navigator = AppModule.navigator,
    private val wordsRepository: WordsRepository = AppModule.wordsRepository,
    private val messageHandler: MessageHandler = AppModule.massageHandler
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
        (maxSeconds downTo 1).asFlow()
            .onEach { secondsLeft ->
                _state.update {
                    it?.copy(
                        secondsString = getString(
                            Res.string.seconds_template,
                            secondsLeft
                        )
                    )
                }
            }
            .onCompletion {
                wordsRepository.addedPointsFlow.tryEmit(state.value?.currentPoints ?: 0)
                navigator.popBackStack()
            }
            .launchIn(viewModelScope)
    }
}
