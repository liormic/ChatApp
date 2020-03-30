package com.ely.lemonade.chatscreen

import androidx.lifecycle.ViewModel
import com.ely.lemonade.database.Message

class MainViewModel : ViewModel() {
    val mainActivityRepository: MainActivityRepository =
        MainActivityRepository()

    fun getCurrentMessage(message: Message) = mainActivityRepository.getCurrentMessage(message)
    fun getServerLiveData() = mainActivityRepository.getServerLiveData()
}