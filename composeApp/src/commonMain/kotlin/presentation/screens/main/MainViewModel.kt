package presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.MessageHandler
import data.exceptionHandler
import data.repositories.GroupsRepository
import data.repositories.WordsRepository
import di.AppModule
import domain.models.GameGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navigation.Navigator
import presentation.Screen

class MainViewModel(
    private val groupsRepository: GroupsRepository = AppModule.groupsRepository,
    private val wordsRepository: WordsRepository = AppModule.wordsRepository,
    private val navigator: Navigator = AppModule.navigator,
    private val messageHandler: MessageHandler = AppModule.massageHandler
) : ViewModel() {

    private val _state = MutableStateFlow<MainViewState?>(null)
    val state = _state.asStateFlow()

    init {
        setInitialState()
        observeGroups()
    }

    fun startGame() {
        navigator.navigate(Screen.RoundResult)
    }

    fun openSettings() {
        navigator.navigate(Screen.Settings)
    }

    private fun setInitialState() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            val currentGroups = groupsRepository.observeGroups().first()

            if (currentGroups.isEmpty()) {
                val newGroups = List(2) { GameGroup.create(name = wordsRepository.loadNewWord()) }
                newGroups.forEach { groupsRepository.addGroup(it) }
                _state.value = MainViewState(groups = newGroups)
            } else {
                _state.value = MainViewState(groups = currentGroups)
            }
        }
    }

    private fun observeGroups() {
        groupsRepository
            .observeGroups()
            .onEach { groups -> _state.update { it?.copy(groups = groups) } }
            .launchIn(viewModelScope)
    }

}
