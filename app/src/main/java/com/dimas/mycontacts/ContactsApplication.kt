package com.dimas.mycontacts

import android.app.Application
import com.dimas.mycontacts.di.initKoin
import org.koin.android.ext.koin.androidContext

class ContactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@ContactsApplication)
        }
    }
}