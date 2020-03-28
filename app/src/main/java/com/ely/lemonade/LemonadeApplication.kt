package com.ely.lemonade

import android.app.Application
import android.content.Context

class LemonadeApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: LemonadeApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}