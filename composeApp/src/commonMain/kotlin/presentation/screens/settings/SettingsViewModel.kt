package presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.MessageHandler
import data.exceptionHandler
import data.repositories.GroupsRepository
import data.repositories.SettingsRepository
import data.repositories.WordsRepository
import di.AppModule
import di.Vibrator
import domain.models.GameGroup
import domain.models.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.screens.settings.SettingsViewAction.AddGroup
import presentation.screens.settings.SettingsViewAction.OnGroupNameChanged
import presentation.screens.settings.SettingsViewAction.OnRemoveGroupClicked
import presentation.screens.settings.SettingsViewAction.SaveFailureWordPoints
import presentation.screens.settings.SettingsViewAction.SaveMaxPoints
import presentation.screens.settings.SettingsViewAction.SaveRoundSeconds
import presentation.screens.settings.SettingsViewAction.SaveSuccessWordPoints
import presentation.screens.settings.SettingsViewAction.SetFailureWordPoints
import presentation.screens.settings.SettingsViewAction.SetMaxPoints
import presentation.screens.settings.SettingsViewAction.SetRoundSeconds
import presentation.screens.settings.SettingsViewAction.SetSuccessWordPoints
import presentation.utils.SliderFractionUtils

class SettingsViewModel(
    private val settingsRepository: SettingsRepository = AppModule.settingsRepository,
    private val groupsRepository: GroupsRepository = AppModule.groupsRepository,
    private val vibrator: Vibrator = AppModule.vibrator,
    private val wordsRepository: WordsRepository = AppModule.wordsRepository,
    private val messageHandler: MessageHandler = AppModule.massageHandler
) : ViewModel() {

    private val _state = MutableStateFlow<SettingsViewState?>(null)
    val state = _state.asStateFlow()

    init {
        setInitialState()
    }

    fun onAction(action: SettingsViewAction) = when (action) {
        AddGroup -> addGroup()
        SaveFailureWordPoints -> saveFailureWordPoints()
        SaveMaxPoints -> saveMaxPoints()
        SaveRoundSeconds -> saveRoundSeconds()
        SaveSuccessWordPoints -> saveSuccessWordPoints()
        is OnRemoveGroupClicked -> onRemoveGroupClicked(action.index)
        is OnGroupNameChanged -> onGroupNameChanged(action.name, action.index)
        is SetFailureWordPoints -> setFailureWordPoints(action.fraction)
        is SetMaxPoints -> setMaxPoints(action.fraction)
        is SetRoundSeconds -> setRoundSeconds(action.fraction)
        is SetSuccessWordPoints -> setSuccessWordPoints(action.fraction)
    }

    private fun onGroupNameChanged(name: String, index: Int) {
        _state.update {
            it?.copy(groups = it.groups.toMutableList().apply { this[index] = this[index].copy(name = name) })
        }

        viewModelScope.launch(exceptionHandler(messageHandler)) {
            groupsRepository.editGroup(checkNotNull(state.value).groups[index])
        }
    }

    private fun onRemoveGroupClicked(index: Int) {
        val group = checkNotNull(state.value).groups[index]

        _state.update {
            it?.copy(groups = it.groups.toMutableList().apply { removeAt(index) })
        }

        viewModelScope.launch(exceptionHandler(messageHandler)) {
            groupsRepository.removeGroup(group)
        }
    }

    private fun addGroup() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            val newGroup = GameGroup.create(name = wordsRepository.loadNewWord())
            _state.update {
                it?.copy(groups = it.groups.toMutableList().apply { add(newGroup) })
            }

            groupsRepository.addGroup(newGroup)
        }
    }

    private fun setMaxPoints(fraction: Float) {
        updateSliderState(
            oldValueFactory = { maxPoints },
            fraction = fraction,
            range = Settings.maxPoints,
            settingsUpdate = { copy(maxPoints = it) }
        )
    }

    private fun setRoundSeconds(fraction: Float) {
        updateSliderState(
            oldValueFactory = { roundSeconds },
            fraction = fraction,
            range = Settings.roundSeconds,
            settingsUpdate = { copy(roundSeconds = it) }
        )
    }

    private fun setSuccessWordPoints(fraction: Float) {
        updateSliderState(
            oldValueFactory = { successWordPoints },
            fraction = fraction,
            range = Settings.successWordPoints,
            settingsUpdate = { copy(successWordPoints = it) }
        )
    }

    private fun setFailureWordPoints(fraction: Float) {
        updateSliderState(
            oldValueFactory = { failureWordPoints },
            fraction = fraction,
            range = Settings.failureWordPoints,
            settingsUpdate = { copy(failureWordPoints = it) }
        )
    }

    private fun saveMaxPoints() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            settingsRepository.saveMaxPoints(checkNotNull(state.value).settings.maxPoints)
        }
    }

    private fun saveRoundSeconds() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            settingsRepository.saveRoundSeconds(checkNotNull(state.value).settings.roundSeconds)
        }
    }

    private fun saveSuccessWordPoints() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            settingsRepository.saveSuccessWordPoints(checkNotNull(state.value).settings.successWordPoints)
        }
    }

    private fun saveFailureWordPoints() {
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
        if (oldValue != newValue) vibrator.vibrate()
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
