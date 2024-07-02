package com.duwna.debelias

import androidx.compose.ui.window.ComposeUIViewController
import com.duwna.debelias.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}