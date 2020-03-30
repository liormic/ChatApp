package com.ely.lemonade.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.ely.lemonade.R
import com.ely.lemonade.database.Message
import com.ely.lemonade.database.Message.Companion.TYPE_BOT_MESSAGE
import com.ely.lemonade.database.Message.Companion.TYPE_SEPERATOR
import com.ely.lemonade.database.Message.Companion.TYPE_USER_MESSAGE
import com.ely.lemonade.database.MessageDiff
import com.ely.lemonade.viewholder.BaseMessageViewHolder
import com.ely.lemonade.viewholder.BotMessageViewHolder
import com.ely.lemonade.viewholder.UserMessageViewHolder

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