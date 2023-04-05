package com.duwna.debelias.presentation.screens.settings

sealed interface SettingsViewAction {

    data class OnGroupNameChanged(val name: String, val index: Int) : SettingsViewAction
    data class OnRemoveGroupClicked(val index: Int) : SettingsViewAction

    data class SetMaxPoints(val fraction: Float) : SettingsViewAction
    data class SetRoundSeconds(val fraction: Float) : SettingsViewAction
    data class SetSuccessWordPoints(val fraction: Float) : SettingsViewAction
    data class SetFailureWordPoints(val fraction: Float) : SettingsViewAction

    object AddGroup : SettingsViewAction
    object SaveMaxPoints : SettingsViewAction
    object SaveRoundSeconds : SettingsViewAction
    object SaveSuccessWordPoints : SettingsViewAction
    object SaveFailureWordPoints : SettingsViewAction
}
