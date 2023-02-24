package com.duwna.debelias.domain.models

import com.duwna.debelias.AppSettingsDto

data class Settings(
    val maxPoints: Int,
    val roundSeconds: Int,
    val successWordPoints: Int,
    val failureWordPoints: Int
) {

    companion object {

        private const val DEFAULT_MAX_POINTS = 60
        private const val DEFAULT_ROUND_SECONDS = 60
        private const val DEFAULT_SUCCESS_WORD_POINTS = 2
        private const val DEFAULT_FAILURE_WORD_POINTS = 1

        val maxPoints = 20..200
        val roundSeconds = 5..150
        val successWordPoints = 1..10
        val failureWordPoints = 0..10

        fun fromDto(dto: AppSettingsDto) = Settings(
            maxPoints = dto.maxPoints.takeIf { it != 0 } ?: DEFAULT_MAX_POINTS,
            roundSeconds = dto.roundSeconds.takeIf { it != 0 } ?: DEFAULT_ROUND_SECONDS,
            successWordPoints = dto.successWordPoints.takeIf { it != 0 } ?: DEFAULT_SUCCESS_WORD_POINTS,
            failureWordPoints = dto.failureWordPoints.takeIf { it != 0 } ?: DEFAULT_FAILURE_WORD_POINTS
        )
    }
}
