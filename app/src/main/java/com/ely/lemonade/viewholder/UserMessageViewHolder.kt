package com.ely.lemonade.viewholder

import android.view.View
import android.widget.TextView
import com.ely.lemonade.database.Message
import com.ely.lemonade.R


class UserMessageViewHolder(view: View) : BaseMessageViewHolder<Message>(view) {
    private val messageContent = view.findViewById<TextView>(R.id.message)

    override fun bind(item: Message) {
        messageContent.text = item.messageContent
    }
}