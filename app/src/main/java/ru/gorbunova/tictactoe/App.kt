package ru.gorbunova.tictactoe

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import io.realm.Realm
import io.realm.RealmConfiguration

class App: Application(){
    companion object {
        val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
        lateinit var appContext: Context
        private const val realmVersion = 1L //число в long-формате
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        initRealm()
    }

    private fun initRealm(){
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
            .schemaVersion(realmVersion)
            .deleteRealmIfMigrationNeeded()
            .build())
    }

}