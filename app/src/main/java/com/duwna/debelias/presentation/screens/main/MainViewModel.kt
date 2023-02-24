package com.duwna.debelias.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.data.exceptionHandler
import com.duwna.debelias.data.repositories.GroupsRepository
import com.duwna.debelias.navigation.Navigator
import com.duwna.debelias.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val groupsRepository: GroupsRepository,
    private val navigator: Navigator,
    private val messageHandler: MessageHandler
) : ViewModel() {

    private val _state = MutableStateFlow<MainViewState?>(null)
    val state = _state.asStateFlow()

    init {
        setInitialState()
    }

    fun startGame() {
        navigator.navigate(Screen.RoundResult)
    }

    fun openSettings() {
        navigator.navigate(Screen.Settings)
    }

    private fun setInitialState() {
        viewModelScope.launch(exceptionHandler(messageHandler)) {
            val groups = groupsRepository.observeGroups().first()
            _state.value = MainViewState(groups = groups)
        }
    }

}
