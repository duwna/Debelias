package di

import data.MessageHandler
import data.PersistentStorage
import data.repositories.GroupsRepository
import data.repositories.SettingsRepository
import data.repositories.WordsRepository
import navigation.NavigatorImpl

object AppModule {

    private val storage by lazy { PersistentStorage() }

    val massageHandler by lazy { MessageHandler() }

    val navigator by lazy { NavigatorImpl() }

    val vibrator by lazy { Vibrator() }

    val settingsRepository by lazy { SettingsRepository(storage) }

    val groupsRepository by lazy { GroupsRepository(storage) }

    val wordsRepository by lazy { WordsRepository() }
}

class Vibrator {
    fun vibrate() {

    }
}