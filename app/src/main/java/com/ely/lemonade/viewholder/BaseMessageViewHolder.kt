package com.ely.lemonade.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseMessageViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}