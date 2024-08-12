package com.duwna.debelias.data.repositories

import com.duwna.debelias.presentation.utils.mutableEventFlow
import debelias_multiplatform.composeapp.generated.resources.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi

class WordsRepository {

    val addedPointsFlow = mutableEventFlow<Int>()

    private var cachedWords: List<String>? = null

    @OptIn(ExperimentalResourceApi::class)
    suspend fun loadNewWord(): String {
        if (cachedWords == null) {
            cachedWords = withContext(Dispatchers.IO) {
                Res.readBytes("files/all_words_file.txt")
                    .decodeToString()
                    .split("\n")
            }
        }

        return cachedWords?.random().orEmpty()
    }
}
