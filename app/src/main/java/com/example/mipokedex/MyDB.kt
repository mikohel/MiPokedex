package com.example.mipokedex
import android.app.Application
import com.example.mipokedex.database.DataBaseManager
class MyDB : Application() {
    override fun onCreate() {
        DataBaseManager.instance.initializeDb(applicationContext)
        super.onCreate()
    }
}