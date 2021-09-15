package com.egaragul.task_4_room_and_cursor

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        private var INSTANCE : App? = null

        fun getAppContext() : Context {
            return INSTANCE!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}