package com.ely.lemonade

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ely.lemonadeserver.Step
import java.util.*

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey var id: String = Calendar.getInstance().timeInMillis.toString(), var messageContent: String = "",
    var messageType: Int = TYPE_USER_MESSAGE,
    var step: String = Step.STEP0.toString(),
    var waitingForResponse: Boolean = false
) {
    companion object {
        const val TYPE_BOT_MESSAGE = 0
        const val TYPE_USER_MESSAGE = 1
        const val TYPE_SEPERATOR = 2
    }
}