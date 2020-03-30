package com.ely.lemonade.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.ely.lemonade.database.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages")
    fun getAllMessagesPaged(): DataSource.Factory<Int, Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message)
}