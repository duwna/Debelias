package presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import debelias_multiplatform.composeapp.generated.resources.Res
import debelias_multiplatform.composeapp.generated.resources.add_button
import debelias_multiplatform.composeapp.generated.resources.slider_failure_word_points
import debelias_multiplatform.composeapp.generated.resources.slider_max_points
import debelias_multiplatform.composeapp.generated.resources.slider_round_seconds
import debelias_multiplatform.composeapp.generated.resources.slider_success_word_points
import domain.models.GameGroup
import domain.models.Settings
import org.jetbrains.compose.resources.stringResource
import presentation.screens.settings.SettingsViewAction.AddGroup
import presentation.screens.settings.SettingsViewAction.OnGroupNameChanged
import presentation.screens.settings.SettingsViewAction.OnRemoveGroupClicked
import presentation.screens.settings.SettingsViewAction.SaveFailureWordPoints
import presentation.screens.settings.SettingsViewAction.SaveMaxPoints
import presentation.screens.settings.SettingsViewAction.SaveRoundSeconds
import presentation.screens.settings.SettingsViewAction.SaveSuccessWordPoints
import presentation.screens.settings.SettingsViewAction.SetFailureWordPoints
import presentation.screens.settings.SettingsViewAction.SetMaxPoints
import presentation.screens.settings.SettingsViewAction.SetRoundSeconds
import presentation.screens.settings.SettingsViewAction.SetSuccessWordPoints
import presentation.screens.settings.composable.GroupInput
import presentation.screens.settings.composable.SliderWithText
import kotlin.reflect.KClass

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return SettingsViewModel() as T
            }
        }
    )
) {

    val state by viewModel.state.collectAsState()

    SettingsScreenView(
        currentState = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun SettingsScreenView(
    currentState: SettingsViewState?,
    onAction: (SettingsViewAction) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        if (currentState != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                currentState.groups.forEachIndexed { index, group ->
                    GroupInput(
                        name = group.name,
                        canRemoveGroup = currentState.canRemoveGroup,
                        modifier = Modifier.fillMaxWidth(),
                        onTextChanged = { onAction(OnGroupNameChanged(it, index)) },
                        onRemoveClicked = { onAction(OnRemoveGroupClicked(index)) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    onClick = { onAction(AddGroup) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(Res.string.add_button))
                }

                Spacer(modifier = Modifier.height(30.dp))

                SliderWithText(
                    selectedText = stringResource(
                        Res.string.slider_round_seconds,
                        currentState.settings.roundSeconds
                    ),
                    selectedValue = currentState.roundSecondsSliderValue,
                    onValueChanged = { onAction(SetRoundSeconds(it)) },
                    onValueChangeFinished = { onAction(SaveRoundSeconds) }
                )

                SliderWithText(
                    selectedText = stringResource(
                        Res.string.slider_max_points,
                        currentState.settings.maxPoints
                    ),
                    selectedValue = currentState.maxPointsSliderValue,
                    onValueChanged = { onAction(SetMaxPoints(it)) },
                    onValueChangeFinished = { onAction(SaveMaxPoints) }
                )

                SliderWithText(
                    selectedText = stringResource(
                        Res.string.slider_success_word_points,
                        currentState.settings.successWordPoints
                    ),
                    selectedValue = currentState.successWordPointsSliderValue,
                    onValueChanged = { onAction(SetSuccessWordPoints(it)) },
                    onValueChangeFinished = { onAction(SaveSuccessWordPoints) }
                )

                SliderWithText(
                    selectedText = stringResource(
                        Res.string.slider_failure_word_points,
                        currentState.settings.failureWordPoints
                    ),
                    selectedValue = currentState.failureWordPointsSliderValue,
                    onValueChanged = { onAction(SetFailureWordPoints(it)) },
                    onValueChangeFinished = { onAction(SaveFailureWordPoints) }
                )
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun SettingsScreenPreview() {
    SettingsScreenView(
        currentState = SettingsViewState(
            groups = listOf(
                GameGroup("1", "name")
            ),
            settings = Settings(
                maxPoints = 0,
                roundSeconds = 0,
                successWordPoints = 0,
                failureWordPoints = 0,
            )
        ),
        onAction = {}
    )
}
