package presentation.screens.round_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.MessageHandler
import data.exceptionHandler
import data.repositories.GroupsRepository
import data.repositories.SettingsRepository
import data.repositories.WordsRepository
import di.AppModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navigation.Navigator
import presentation.Screen

class RoundResultViewModel(
    private val settingsRepository: SettingsRepository = AppModule.settingsRepository,
    private val groupsRepository: GroupsRepository = AppModule.groupsRepository,
    private val navigator: Navigator = AppModule.navigator,
    private val wordsRepository: WordsRepository = AppModule.wordsRepository,
    private val messageHandler: MessageHandler = AppModule.massageHandler
) : ViewModel() {

    private val _state = MutableStateFlow<RoundResultViewState?>(null)
    val state = _state.asStateFlow()

    init {
        setInitialState()
        observeAddedPoints()
    }

    private fun setInitialState() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            val playingGroups = groupsRepository.observeGroups().first()
                .map { RoundResultViewState.PlayingGroup(points = 0, group = it) }

            _state.value = RoundResultViewState(
                playingGroups = playingGroups,
                maxPoints = settingsRepository.observeSettings().first().maxPoints
            )
        }
    }

    fun continueGame() {
        navigator.navigate(Screen.GamePlaying)
    }

    fun onExitClicked() {
        navigator.popBackStack()
    }

    private fun observeAddedPoints() {
        wordsRepository.addedPointsFlow
            .onEach { points -> _state.update { it?.updatePoints(points) } }
            .launchIn(viewModelScope)
    }

    private fun RoundResultViewState.updatePoints(points: Int): RoundResultViewState {
        return copy(
            playingGroups = playingGroups.toMutableList().apply {
                val group = this[nextPlayingGroupIndex]
                this[nextPlayingGroupIndex] = group.copy(points = group.points + points)
            },
            nextPlayingGroupIndex = when (nextPlayingGroupIndex) {
                playingGroups.lastIndex -> 0
                else -> nextPlayingGroupIndex + 1
            },
            addedPoints = points
        )
    }

}
