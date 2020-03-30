package com.ely.lemonade

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import com.ely.lemonade.Message.Companion.TYPE_BOT_MESSAGE
import com.ely.lemonade.Message.Companion.TYPE_SEPERATOR
import com.ely.lemonade.Message.Companion.TYPE_USER_MESSAGE

class MessagesAdapter : PagedListAdapter<Message, BaseMessageViewHolder<*>>(MessageDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMessageViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_BOT_MESSAGE -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.bot_message_item, parent, false)
                BotMessageViewHolder(view)
            }
            TYPE_USER_MESSAGE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_message_item, parent, false)
                UserMessageViewHolder(view)
            }

            TYPE_SEPERATOR -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.seperator_itemt, parent, false)
                UserMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        var itemType = 1
        if (item != null) {
            itemType = item.messageType
        }
        return itemType
    }

    override fun onBindViewHolder(holder: BaseMessageViewHolder<*>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            when (holder) {
                is BotMessageViewHolder -> holder.bind(item)
                is UserMessageViewHolder -> holder.bind(item)
                else -> throw IllegalArgumentException()
            }
        }
    }
}