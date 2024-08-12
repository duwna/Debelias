@file:OptIn(ExperimentalMaterial3Api::class)

package com.duwna.debelias.presentation.screens.settings.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import debelias_multiplatform.composeapp.generated.resources.Res
import debelias_multiplatform.composeapp.generated.resources.icon_remove
import org.jetbrains.compose.resources.painterResource

@Composable
fun GroupInput(
    name: String,
    canRemoveGroup: Boolean,
    modifier: Modifier,
    onTextChanged: (String) -> Unit,
    onRemoveClicked: () -> Unit
) {
    TextField(
        modifier = modifier,
        value = name,
        onValueChange = onTextChanged,
        trailingIcon = {
            if (canRemoveGroup) {
                Image(
                    painter = painterResource(Res.drawable.icon_remove),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    contentDescription = "remove member",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = onRemoveClicked)
                )
            }
        }
    )
}
