package com.ely.lemonadeserver

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson

const val DELAY = 2500L

class LemonadeServer(var mutableLiveData: MutableLiveData<String>) {
        fun getCurrentMessage(request: String) {
            val receivedMessage : Message = Gson().fromJson(request,Message::class.java)
            Handler().postDelayed({
                if(receivedMessage.step != Step.STEP6.toString())
                mutableLiveData.value =  MessageEngine.getCurrentMessage(receivedMessage.step, receivedMessage.messageContent)

            }, DELAY)
        }
}