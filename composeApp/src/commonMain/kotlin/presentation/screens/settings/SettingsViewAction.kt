package presentation.screens.settings

sealed interface SettingsViewAction {

    data class OnGroupNameChanged(val name: String, val index: Int) : SettingsViewAction
    data class OnRemoveGroupClicked(val index: Int) : SettingsViewAction

    data class SetMaxPoints(val fraction: Float) : SettingsViewAction
    data class SetRoundSeconds(val fraction: Float) : SettingsViewAction
    data class SetSuccessWordPoints(val fraction: Float) : SettingsViewAction
    data class SetFailureWordPoints(val fraction: Float) : SettingsViewAction

    data object AddGroup : SettingsViewAction
    data object SaveMaxPoints : SettingsViewAction
    data object SaveRoundSeconds : SettingsViewAction
    data object SaveSuccessWordPoints : SettingsViewAction
    data object SaveFailureWordPoints : SettingsViewAction
}
