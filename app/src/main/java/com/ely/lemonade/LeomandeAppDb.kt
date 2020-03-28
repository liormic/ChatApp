package com.ely.lemonade

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class LeomandeAppDb : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile private var instance: LeomandeAppDb? = null
        fun getInstance(context: Context): LeomandeAppDb {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): LeomandeAppDb {
            return Room.databaseBuilder(context, LeomandeAppDb::class.java, context.getString(R.string.db_name)).build()
        }
    }
}