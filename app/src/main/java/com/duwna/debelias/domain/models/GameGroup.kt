package com.duwna.debelias.domain.models

import com.duwna.debelias.GroupDto
import java.util.UUID

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
            id = UUID.randomUUID().toString()
        )
    }
}
