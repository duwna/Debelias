package presentation.screens.round_result.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.models.GameGroup
import presentation.composables.PointsCounter
import presentation.screens.round_result.RoundResultViewState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PlayingGroupCard(
    playingGroup: RoundResultViewState.PlayingGroup,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(if (isSelected) 16.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .align(CenterHorizontally)
        ) {
            Text(
                text = playingGroup.group.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(CenterVertically)
            )

            Spacer(modifier = Modifier.weight(1f))

            PointsCounter(
                value = playingGroup.points,
                modifier = Modifier
                    .height(30.dp)
                    .widthIn(30.dp)
            )
        }
    }
}

@Composable
@Preview()
fun GroupCardPreview() {
    PlayingGroupCard(
        playingGroup = RoundResultViewState.PlayingGroup(
            points = 3,
            group = GameGroup(id = "id", name = "Name")
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
