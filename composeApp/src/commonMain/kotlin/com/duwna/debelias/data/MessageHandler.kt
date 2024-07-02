package com.duwna.debelias.data

import com.duwna.debelias.presentation.utils.mutableEventFlow
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow

class MessageHandler {

    private val messageFlow = mutableEventFlow<MessageEvent>()

    fun observeMessages(): Flow<MessageEvent> = messageFlow

    fun showMessage(errorEvent: MessageEvent) {
        messageFlow.tryEmit(errorEvent)
    }

}

sealed class MessageEvent(val durationMillis: Long = DURATION) {
    companion object {
        private const val DURATION = 1000L
    }

    abstract fun text(): String

    class Message(val message: String, durationMillis: Long = DURATION) :
        MessageEvent(durationMillis) {
        override fun text(): String = message
    }

    class Error(val throwable: Throwable, durationMillis: Long = DURATION) :
        MessageEvent(durationMillis) {
        override fun text(): String = throwable.message.orEmpty()
    }
}

fun exceptionHandler(messageHandler: MessageHandler) = CoroutineExceptionHandler { _, throwable ->
//    Timber.tag("CoroutineExceptionHandler").e(throwable)
    messageHandler.showMessage(MessageEvent.Error(throwable))
}
