package com.example.expenses

import android.app.Application
import android.content.Context
import androidx.room.*

class MyApplication : Application() {
    companion object {
        lateinit var context : Context
        lateinit var db_name : String
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        db_name = "app_database"
    }
}