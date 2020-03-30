package com.ely.lemonade.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ely.lemonade.Constants
import com.ely.lemonade.LemonadeApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MessageRepository : CoroutineScope {

    private var messagesLiveData: LiveData<PagedList<Message>>
    private var messagesDao: MessageDao =
        LeomandeAppDb.getInstance(LemonadeApplication.applicationContext()).messageDao()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    init {
        val factory: DataSource.Factory<Int, Message> =
            messagesDao.getAllMessagesPaged()
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(Constants.DB_PAGE_SIZE)
            .setPrefetchDistance(Constants.DB_PREFETCH_SIZE)
            .build()
        val pagedListBuilder: LivePagedListBuilder<Int, Message> =
            LivePagedListBuilder<Int, Message>(
                factory,
                config
            )
        messagesLiveData = pagedListBuilder.build()
    }

    fun setMessage(message: Message) {
        launch { insertMessage(message) }
    }

    private suspend fun insertMessage(message: Message) {
        withContext(Dispatchers.IO) {
            messagesDao.insertMessage(message)
        }
    }

    fun getMessagesLiveData() = messagesLiveData
}