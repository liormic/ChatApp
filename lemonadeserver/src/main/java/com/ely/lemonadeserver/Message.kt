package com.ely.lemonadeserver

data class Message ( var id: String, var messageContent:String, var messageType:Int, val step:String,  var waitingForResponse: Boolean){
    companion object {
        const val TYPE_BOT_MESSAGE = 0
    }
}