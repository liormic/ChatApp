package com.ely.lemonade
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message (@PrimaryKey var id: String, var messageContent:String, var messageType:Int){
    companion object {
        const val TYPE_BOT_MESSAGE = 0
        const val TYPE_USER_MESSAGE = 1
    }
}