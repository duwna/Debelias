package com.duwna.debelias.platform

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.getSystemService

actual class VibrationManager(context: Context) {

    private val vibrator = context.getSystemService<Vibrator>()

    actual fun vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(1, 50))
        }
    }
}