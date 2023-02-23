package com.duwna.debelias.data.repositories

import com.duwna.debelias.data.PersistentStorage
import com.duwna.debelias.domain.models.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val persistentStorage: PersistentStorage
) {

    fun observeSettings(): Flow<Settings> = persistentStorage.observeSettings()
        .map(Settings.Companion::fromDto)

    suspend fun saveMaxPoints(value: Int) = persistentStorage.saveSettings {
        setMaxPoints(value)
    }

    suspend fun saveRotationsCount(value: Int) = persistentStorage.saveSettings {
        setMaxSeconds(value)
    }
}
