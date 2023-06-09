package com.duwna.debelias.data.proto

import androidx.datastore.core.Serializer
import com.duwna.debelias.AppSettingsDto
import java.io.InputStream
import java.io.OutputStream

class AppSettingsSerializer : Serializer<AppSettingsDto> {

    override val defaultValue: AppSettingsDto = AppSettingsDto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppSettingsDto =
        AppSettingsDto.parseFrom(input)

    override suspend fun writeTo(t: AppSettingsDto, output: OutputStream) =
        t.writeTo(output)
}
