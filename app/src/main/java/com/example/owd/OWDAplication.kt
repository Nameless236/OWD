package com.example.owd

import android.app.Application
import com.example.owd.data.AppContainer
import com.example.owd.data.AppDataContainer

class Owdapplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}