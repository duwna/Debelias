package com.duwna.debelias.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duwna.debelias.R

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
                    value >= 0 -> MaterialTheme.colors.primary
                    else -> MaterialTheme.colors.error
                },
                shape = CutCornerShape(4.dp)
            )
    ) {
        Text(
            text = when {
                showSign && value >= 0 -> stringResource(R.string.added_points_template, value)
                else -> value.toString()
            },
            color = MaterialTheme.colors.onPrimary,
            fontSize = textSize,
            modifier = Modifier.align(Alignment.Center)
        )
    }

}

@Preview(showSystemUi = true)
@Composable
private fun PointsCounterPreview() {
    Column {
        PointsCounter(value = 2, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.height(20.dp))
        PointsCounter(value = -13, modifier = Modifier.size(40.dp))
    }
}
