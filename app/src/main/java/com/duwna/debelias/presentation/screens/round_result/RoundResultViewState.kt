package com.duwna.debelias.presentation.screens.round_result

import com.duwna.debelias.domain.models.GameGroup

data class RoundResultViewState(
    val playingGroups: List<PlayingGroup>,
    val maxPoints: Int,
    val nextPlayingGroupIndex: Int = 0,
    val addedPoints: Int? = null
) {

    data class PlayingGroup(
        val points: Int,
        val group: GameGroup
    )

    val nextGroupName: String
        get() = playingGroups[nextPlayingGroupIndex].group.name

    fun isGroupSelected(index: Int): Boolean {
        addedPoints ?: return false

        return when (nextPlayingGroupIndex) {
            0 -> index == playingGroups.lastIndex
            else -> index == nextPlayingGroupIndex - 1
        }
    }

}
