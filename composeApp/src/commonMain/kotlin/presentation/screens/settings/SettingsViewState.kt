package presentation.screens.settings

import presentation.utils.SliderFractionUtils
import domain.models.GameGroup
import domain.models.Settings
import domain.models.Settings.Companion.failureWordPoints
import domain.models.Settings.Companion.maxPoints
import domain.models.Settings.Companion.roundSeconds
import domain.models.Settings.Companion.successWordPoints

data class SettingsViewState(
    val groups: List<GameGroup>,
    val settings: Settings
) {

    val canRemoveGroup: Boolean
        get() = groups.size > 2

    val maxPointsSliderValue: Float
        get() = SliderFractionUtils.getSliderFraction(maxPoints, settings.maxPoints)

    val roundSecondsSliderValue: Float
        get() = SliderFractionUtils.getSliderFraction(roundSeconds, settings.roundSeconds)

    val successWordPointsSliderValue: Float
        get() = SliderFractionUtils.getSliderFraction(successWordPoints, settings.successWordPoints)

    val failureWordPointsSliderValue: Float
        get() = SliderFractionUtils.getSliderFraction(failureWordPoints, settings.failureWordPoints)

}
