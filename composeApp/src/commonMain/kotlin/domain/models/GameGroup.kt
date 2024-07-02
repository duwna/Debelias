package domain.models

import kotlin.random.Random

data class GameGroup(
    val id: String,
    val name: String
) {

    companion object {

        fun fromDto(dto: GroupDto) = GameGroup(
            id = dto.id,
            name = dto.name
        )

        fun create(name: String) = GameGroup(
            name = name,
            id = Random.nextLong().toString()
        )
    }
}

data class GroupDto(
    val id: String,
    val name: String
)