package com.duwna.debelias.data

import android.content.Context
import androidx.annotation.RawRes
import java.io.InputStream
import javax.inject.Inject

class ResourceManager @Inject constructor(
    private val context: Context
) {

    fun string(id: Int, vararg args: Any): String =
        context.getString(id, args)

    fun stringArray(id: Int): Array<String> =
        context.resources.getStringArray(id)

    fun getRawStream(@RawRes idRes: Int): InputStream =
        context.resources.openRawResource(idRes)

}
