package com.ely.lemonade

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages")
    fun getAll(): LiveData<List<Message>>

    @Query("SELECT * FROM messages")
    fun getAllMessagesPaged(): DataSource.Factory<Int, Message>

    @Insert
    fun insertAll(messages: List<Message>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message)

    @Delete
    fun delete(message: Message)

}