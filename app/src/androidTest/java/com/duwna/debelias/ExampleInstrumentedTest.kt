package com.duwna.debelias

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @get:Rule(order = 1)
    val compose = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun test_word_is_displayed() {

        compose.onNodeWithText("Начать").performClick()

        compose.onNodeWithText("Начать").performClick()

        compose.onNodeWithTag("box_tag").assertIsDisplayed()

        runBlocking { delay(500) } // wait async work

        compose.onNodeWithTag("word_tag").assertIsDisplayed()
    }
}