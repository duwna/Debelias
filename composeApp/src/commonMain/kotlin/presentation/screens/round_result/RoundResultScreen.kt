package presentation.screens.round_result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import debelias_multiplatform.composeapp.generated.resources.Res
import debelias_multiplatform.composeapp.generated.resources.continue_game
import debelias_multiplatform.composeapp.generated.resources.start_game
import org.jetbrains.compose.resources.stringResource
import presentation.composables.PointsCounter
import presentation.screens.round_result.composables.GameEndDialog
import presentation.screens.round_result.composables.PlayingGroupCard
import presentation.screens.round_result.composables.TopCard
import kotlin.reflect.KClass

@Composable
fun RoundResultScreen(
    viewModel: RoundResultViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return RoundResultViewModel() as T
            }
        }
    )
) {

    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val currentState = state

        if (currentState != null) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                TopCard(
                    maxPoints = currentState.maxPoints,
                    onExitClicked = viewModel::onExitClicked
                )

                Spacer(modifier = Modifier.height(40.dp))

                currentState
                    .playingGroups
                    .forEachIndexed { index, group ->
                        PlayingGroupCard(
                            playingGroup = group,
                            isSelected = currentState.isGroupSelected(index)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                Spacer(modifier = Modifier.weight(1f))

                if (currentState.addedPoints != null) {
                    PointsCounter(
                        value = currentState.addedPoints,
                        showSign = true,
                        modifier = Modifier
                            .height(40.dp)
                            .widthIn(40.dp)
                            .align(CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = viewModel::continueGame) {
                    Text(
                        text = stringResource(
                            when (currentState.addedPoints) {
                                null -> Res.string.start_game
                                else -> Res.string.continue_game
                            }
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = currentState.nextGroupName)
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            currentState.getVictoryGroupName()?.let {
                GameEndDialog(groupName = it, onDismiss = viewModel::onExitClicked)
            }

        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
