package com.duwna.debelias.data.repositories

import com.duwna.debelias.R
import com.duwna.debelias.data.ResourceManager
import com.duwna.debelias.presentation.utils.mutableEventFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordsRepository @Inject constructor(
    private val resourceManager: ResourceManager
) {

    val addedPointsFlow = mutableEventFlow<Int>()

    suspend fun loadNewWord(): String = withContext(Dispatchers.IO) {
        resourceManager.openRawStream(R.raw.all_words_file)
            .use { String(it.readBytes()) }
            .split("\n")
            .random()
    }
}
