package com.duwna.debelias.presentation.screens.round_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.data.exceptionHandler
import com.duwna.debelias.data.repositories.GroupsRepository
import com.duwna.debelias.data.repositories.SettingsRepository
import com.duwna.debelias.data.repositories.WordsRepository
import com.duwna.debelias.navigation.Navigator
import com.duwna.debelias.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoundResultViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val groupsRepository: GroupsRepository,
    private val navigator: Navigator,
    private val wordsRepository: WordsRepository,
    private val messageHandler: MessageHandler
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
