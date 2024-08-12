package com.duwna.debelias.data

import com.duwna.debelias.domain.models.AppSettingsDto
import com.duwna.debelias.domain.models.GroupDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class PersistentStorage {

    companion object {
        private const val APP_SETTINGS_FILE_NAME = "app_settings.pb"
        private const val GROUPS_FILE_NAME = "groups.pb"
    }

    private val settingsFlow = MutableStateFlow(AppSettingsDto())
    private val groupsFlow = MutableStateFlow(emptyList<GroupDto>())

    fun observeSettings(): Flow<AppSettingsDto> = settingsFlow

    suspend fun saveSettings(action: (AppSettingsDto) -> AppSettingsDto) {
        settingsFlow.value = action(settingsFlow.value)
    }

    fun observeGroups(): Flow<List<GroupDto>> = groupsFlow

    suspend fun saveGroups(action: (List<GroupDto>) -> List<GroupDto>) {
        groupsFlow.value = action(groupsFlow.value)
    }

}
