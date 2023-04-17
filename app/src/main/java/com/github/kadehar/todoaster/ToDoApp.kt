package com.github.kadehar.todoaster

import android.app.Application
import com.github.kadehar.todoaster.di.appModule
import com.github.kadehar.todoaster.di.navModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ToDoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ToDoApp)
            modules(appModule, navModule)
        }
    }
}