package com.duwna.debelias.data.repositories

import com.duwna.debelias.data.PersistentStorage
import com.duwna.debelias.domain.models.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val persistentStorage: PersistentStorage
) {

    fun observeSettings(): Flow<Settings> = persistentStorage.observeSettings()
        .map(Settings.Companion::fromDto)

    suspend fun saveMaxPoints(value: Int) = persistentStorage.saveSettings {
        it.copy(maxPoints = value)
    }

    suspend fun saveRoundSeconds(value: Int) = persistentStorage.saveSettings {
        it.copy(roundSeconds = value)
    }

    suspend fun saveSuccessWordPoints(value: Int) = persistentStorage.saveSettings {
        it.copy(successWordPoints = value)
    }

    suspend fun saveFailureWordPoints(value: Int) = persistentStorage.saveSettings {
        it.copy(failureWordPoints = value)
    }
}
