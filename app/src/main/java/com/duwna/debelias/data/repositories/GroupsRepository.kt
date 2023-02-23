package com.duwna.debelias.data.repositories

import com.duwna.debelias.GroupDto
import com.duwna.debelias.data.PersistentStorage
import com.duwna.debelias.domain.models.GameGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupsRepository @Inject constructor(
    private val persistentStorage: PersistentStorage
) {

    fun observeGroups(): Flow<List<GameGroup>> =
        persistentStorage.observeGroups().map {
            it.groupsList.map(GameGroup.Companion::fromDto)
        }

    suspend fun getGroup(id: String): GameGroup = observeGroups()
        .map { it.first { group -> group.id == id } }
        .first()

    suspend fun saveGroup(group: GameGroup) {
        val index = observeGroups()
            .map { it.indexOfFirst { groupDto -> groupDto.id == group.id } }
            .first()

        val groupDto = GroupDto.newBuilder()
            .setId(group.id)
            .setName(group.name)
            .build()

        persistentStorage.saveGroups {
            when {
                index < 0 -> addGroups(groupDto)
                else -> setGroups(index, groupDto)
            }
        }
    }
}
