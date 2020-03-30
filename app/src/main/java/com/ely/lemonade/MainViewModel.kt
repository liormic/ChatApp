package com.ely.lemonade

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val mainActivityRepository: MainActivityRepository = MainActivityRepository()

    fun getCurrentMessage(message: Message) = mainActivityRepository.getCurrentMessage(message)
    fun getServerLiveData() = mainActivityRepository.getServerLiveData()


}