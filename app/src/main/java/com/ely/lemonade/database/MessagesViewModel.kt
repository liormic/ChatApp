package com.ely.lemonade.database

import androidx.lifecycle.ViewModel


class MessagesViewModel : ViewModel() {

    private var messageRepository: MessageRepository =
        MessageRepository()

    fun getMessagesLiveData() = messageRepository.getMessagesLiveData()
    fun setMessage(message: Message) = messageRepository.setMessage(message)
}