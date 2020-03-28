package com.ely.lemonade

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.ely.lemonade.Message.Companion.TYPE_USER_MESSAGE
import com.ely.lemonade.Message.Companion.TYPE_BOT_MESSAGE

class MessagesAdapter (val context: Context) : PagedListAdapter<Message, BaseMessageViewHolder<Message>>(MessageDiff()) {


    override fun onBindViewHolder(holder: BaseMessageViewHolder<Message>, position: Int) {
        val item = getItem(position)
        if(item != null) {
            when (holder) {
                is BotMessageViewHolder -> holder.bind(item)
                is UserMessageViewHolder -> holder.bind(item)
                else -> throw IllegalArgumentException()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMessageViewHolder<Message> {
        val context = parent.context
        return when (viewType) {
            TYPE_BOT_MESSAGE -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.bot_message_item, parent, false)
                BotMessageViewHolder(view)
            }
            TYPE_USER_MESSAGE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_message_item, parent, false)
                UserMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
}