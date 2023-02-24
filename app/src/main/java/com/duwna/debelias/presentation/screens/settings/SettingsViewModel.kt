package com.duwna.debelias.presentation.screens.settings

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.data.exceptionHandler
import com.duwna.debelias.data.repositories.GroupsRepository
import com.duwna.debelias.data.repositories.SettingsRepository
import com.duwna.debelias.data.repositories.WordsRepository
import com.duwna.debelias.domain.models.GameGroup
import com.duwna.debelias.domain.models.Settings
import com.duwna.debelias.presentation.utils.SliderFractionUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val groupsRepository: GroupsRepository,
    private val vibrator: Vibrator,
    private val wordsRepository: WordsRepository,
    private val messageHandler: MessageHandler
) : ViewModel() {

    private val _state = MutableStateFlow<SettingsViewState?>(null)
    val state = _state.asStateFlow()

    private val sliderVibrationEffect = VibrationEffect.createOneShot(1, 50)

    init {
        setInitialState()
    }

    fun onGroupNameChanged(name: String, index: Int) {
        _state.update {
            it?.copy(groups = it.groups.toMutableList().apply { this[index] = this[index].copy(name = name) })
        }

        viewModelScope.launch(exceptionHandler(messageHandler)) {
            groupsRepository.editGroup(checkNotNull(state.value).groups[index])
        }
    }

    fun onRemoveGroupClicked(index: Int) {
        val group = checkNotNull(state.value).groups[index]

        _state.update {
            it?.copy(groups = it.groups.toMutableList().apply { removeAt(index) })
        }

        viewModelScope.launch(exceptionHandler(messageHandler)) {
            groupsRepository.removeGroup(group)
        }
    }

    fun addGroup() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            val newGroup = GameGroup.create(name = wordsRepository.loadNewWord())
            _state.update {
                it?.copy(groups = it.groups.toMutableList().apply { add(newGroup) })
            }

            groupsRepository.addGroup(newGroup)
        }
    }

    fun setMaxPoints(fraction: Float) {
        updateSliderState(
            oldValueFactory = { maxPoints },
            fraction = fraction,
            range = Settings.maxPoints,
            settingsUpdate = { copy(maxPoints = it) }
        )
    }

    fun setRoundSeconds(fraction: Float) {
        updateSliderState(
            oldValueFactory = { roundSeconds },
            fraction = fraction,
            range = Settings.roundSeconds,
            settingsUpdate = { copy(roundSeconds = it) }
        )
    }

    fun setSuccessWordPoints(fraction: Float) {
        updateSliderState(
            oldValueFactory = { successWordPoints },
            fraction = fraction,
            range = Settings.successWordPoints,
            settingsUpdate = { copy(successWordPoints = it) }
        )
    }

    fun setFailureWordPoints(fraction: Float) {
        updateSliderState(
            oldValueFactory = { failureWordPoints },
            fraction = fraction,
            range = Settings.failureWordPoints,
            settingsUpdate = { copy(failureWordPoints = it) }
        )
    }

    fun saveMaxPoints() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            settingsRepository.saveMaxPoints(checkNotNull(state.value).settings.maxPoints)
        }
    }

    fun saveRoundSeconds() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            settingsRepository.saveRoundSeconds(checkNotNull(state.value).settings.roundSeconds)
        }
    }

    fun saveSuccessWordPoints() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            settingsRepository.saveSuccessWordPoints(checkNotNull(state.value).settings.successWordPoints)
        }
    }

    fun saveFailureWordPoints() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            settingsRepository.saveFailureWordPoints(checkNotNull(state.value).settings.failureWordPoints)
        }
    }

    private fun updateSliderState(
        oldValueFactory: Settings.() -> Int,
        fraction: Float,
        range: IntRange,
        settingsUpdate: Settings.(Int) -> Settings
    ) {
        val oldValue = oldValueFactory.invoke(checkNotNull(state.value).settings)
        val newValue = SliderFractionUtils.getValueFromFraction(range, fraction)

        _state.update { it?.copy(settings = settingsUpdate(it.settings, newValue)) }
        if (oldValue != newValue) vibrator.vibrate(sliderVibrationEffect)
    }

    private fun setInitialState() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            _state.value = SettingsViewState(
                groups = groupsRepository.observeGroups().first(),
                settings = settingsRepository.observeSettings().first()
            )
        }
    }

}
