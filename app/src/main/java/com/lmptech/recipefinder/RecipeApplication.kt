package com.lmptech.recipefinder

import android.app.Application
import com.lmptech.recipefinder.data.AppContainer
import com.lmptech.recipefinder.data.AppContainerImpl



class RecipeApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(this)
    }
}