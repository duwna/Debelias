package com.duwna.debelias.domain.models

import com.duwna.debelias.AppSettingsDto

data class Settings(
    val maxPoints: Int,
    val maxSeconds: Int
) {

    companion object {

        private const val DEFAULT_MAX_POINTS = 3
        private const val DEFAULT_MAX_SECONDS = 10

        val maxPoints = 5..100
        val maxSeconds = 20..100

        fun fromDto(dto: AppSettingsDto) = Settings(
            maxPoints = dto.maxPoints.takeIf { it != 0 } ?: DEFAULT_MAX_POINTS,
            maxSeconds = dto.maxSeconds.takeIf { it != 0 } ?: DEFAULT_MAX_SECONDS,
        )
    }
}
