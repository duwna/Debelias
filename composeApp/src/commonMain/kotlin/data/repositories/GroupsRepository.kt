package data.repositories

import data.PersistentStorage
import domain.models.GameGroup
import domain.models.GroupDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GroupsRepository(
    private val persistentStorage: PersistentStorage
) {
    fun observeGroups(): Flow<List<GameGroup>> =
        persistentStorage.observeGroups().map {
            it.map(GameGroup.Companion::fromDto)
        }

    suspend fun addGroup(group: GameGroup) {
        val groupDto = GroupDto(
            id = group.id,
            name = group.name
        )

        persistentStorage.saveGroups {
            it + groupDto
        }
    }

    suspend fun editGroup(group: GameGroup) {
        val index = observeGroups()
            .map { it.indexOfFirst { groupDto -> groupDto.id == group.id } }
            .first()

        val groupDto = GroupDto(
            id = group.id,
            name = group.name
        )

        persistentStorage.saveGroups {
            it.toMutableList().apply { this[index] = groupDto }
        }
    }

    suspend fun removeGroup(group: GameGroup) {
        val index = observeGroups()
            .map { it.indexOfFirst { groupDto -> groupDto.id == group.id } }
            .first()

        persistentStorage.saveGroups {
            it.toMutableList().apply { removeAt(index) }
        }
    }
}
