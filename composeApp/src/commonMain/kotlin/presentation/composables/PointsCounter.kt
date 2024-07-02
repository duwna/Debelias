package presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import debelias_multiplatform.composeapp.generated.resources.Res
import debelias_multiplatform.composeapp.generated.resources.added_points_template
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PointsCounter(
    value: Int,
    modifier: Modifier = Modifier,
    showSign: Boolean = false,
    textSize: TextUnit = 20.sp
) {
    Box(
        modifier = modifier
            .background(
                color = when {
                    value >= 0 -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.error
                },
                shape = CutCornerShape(4.dp)
            )
    ) {
        Res.string
        Text(
            text = when {
                showSign && value >= 0 -> stringResource(Res.string.added_points_template, value)
                else -> value.toString()
            },
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = textSize,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 4.dp)
        )
    }

}

@Preview
@Composable
private fun PointsCounterPreview() {
    Column(Modifier.fillMaxWidth()) {
        PointsCounter(
            value = 2333, modifier = Modifier
                .height(40.dp)
                .widthIn(40.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        PointsCounter(
            value = -13, modifier = Modifier
                .height(40.dp)
                .widthIn(40.dp)
        )
    }
}
