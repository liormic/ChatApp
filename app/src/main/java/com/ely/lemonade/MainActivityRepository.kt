package com.ely.lemonade

import androidx.lifecycle.MutableLiveData
import com.ely.lemonadeserver.LemonadeServer
import com.google.gson.Gson

class MainActivityRepository {
    private var serverLiveData: MutableLiveData<String> = MutableLiveData()
    private var lemonadeServer: LemonadeServer = LemonadeServer(serverLiveData)

    fun getCurrentMessage(message: Message) {
        lemonadeServer.getCurrentMessage(Gson().toJson(message))
    }

    fun getServerLiveData(): MutableLiveData<String> = serverLiveData
}