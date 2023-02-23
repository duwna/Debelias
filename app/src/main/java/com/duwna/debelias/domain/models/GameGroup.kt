package com.duwna.debelias.domain.models

import com.duwna.debelias.GroupDto

data class GameGroup(
    val id: String,
    val name: String
) {

    companion object {

        fun fromDto(dto: GroupDto) = GameGroup(
            id = dto.id,
            name = dto.name
        )
    }
}
