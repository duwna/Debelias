package com.duwna.debelias.data.repositories

import com.duwna.debelias.presentation.utils.mutableEventFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.random.Random

class WordsRepository {

    val addedPointsFlow = mutableEventFlow<Int>()

    suspend fun loadNewWord(): String = withContext(Dispatchers.IO) {
//        resourceManager.openRawStream(R.raw.all_words_file)
//            .use { String(it.readBytes()) }
//            .split("\n")
//            .random()
        Random.nextLong().toString()
    }
}
