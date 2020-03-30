package com.ely.lemonade

import android.view.View
import android.widget.TextView


class BotMessageViewHolder(view: View) : BaseMessageViewHolder<Message>(view) {
    private val messageContent = view.findViewById<TextView>(R.id.message)

    override fun bind(item: Message) {
        messageContent.text = item.messageContent
    }
}