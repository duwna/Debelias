package com.duwna.debelias.presentation.screens.game_playing.composables

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

private const val TEXT_SCALE_REDUCTION_INTERVAL = 0.9f

@Composable
fun AutoSizedText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle = LocalTextStyle.current,
    targetTextSizeHeight: TextUnit = style.fontSize,
    maxLines: Int = 1,
) {
    var textSize by remember(text) { mutableStateOf(targetTextSizeHeight) }
    var alpha by remember(text) { mutableStateOf(0f) }

    Text(
        modifier = modifier.alpha(alpha),
        text = text,
        color = color,
        textAlign = textAlign,
        fontSize = textSize,
        fontFamily = style.fontFamily,
        fontStyle = style.fontStyle,
        fontWeight = style.fontWeight,
        lineHeight = style.lineHeight,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult ->
            val maxCurrentLineIndex = textLayoutResult.lineCount - 1

            if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                textSize = textSize.times(TEXT_SCALE_REDUCTION_INTERVAL)
            } else {
                alpha = 1f
            }
        },
    )
}
