package com.duwna.debelias.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.duwna.debelias.data.MessageEvent
import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.presentation.utils.noRippleClickable
import debelias_multiplatform.composeapp.generated.resources.Res
import debelias_multiplatform.composeapp.generated.resources.app_name
import debelias_multiplatform.composeapp.generated.resources.icon_arrow_back
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnackbarScaffold(
    showBackIcon: Boolean,
    onBackClick: () -> Unit,
    messageHandler: MessageHandler,
    content: @Composable (PaddingValues) -> Unit
) {

    val scaffoldState = remember { SnackbarHostState() }

    val defaultSnackbarColor = MaterialTheme.colorScheme.primary
    val errorSnackbarColor = MaterialTheme.colorScheme.error
    var snackbarColor by remember { mutableStateOf(defaultSnackbarColor) }

    LaunchedEffect(Unit) {
        messageHandler
            .observeMessages()
            .collect { event ->
                scaffoldState.currentSnackbarData?.dismiss()

                val showJob = launch {
                    snackbarColor = if (event is MessageEvent.Error) errorSnackbarColor
                    else defaultSnackbarColor

                    scaffoldState.showSnackbar(
                        message = event.text(),
                        duration = SnackbarDuration.Indefinite
                    )
                }

                launch {
                    delay(event.durationMillis)
                    showJob.cancel()
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(16.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = stringResource(Res.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.displaySmall
                    )
                },
                navigationIcon = {
                    AnimatedVisibility(visible = showBackIcon) {
                        Icon(
                            painter = painterResource(Res.drawable.icon_arrow_back),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = "Back",
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .noRippleClickable(onBackClick)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(SnackbarHostState()) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = snackbarColor,
                    modifier = Modifier.noRippleClickable {
                        scaffoldState.currentSnackbarData?.dismiss()
                    }
                )
            }
        }
    ) { padding ->
        content(padding)
    }
}
