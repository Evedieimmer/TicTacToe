package ru.gorbunova.tictactoe

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class App: Application(){
    companion object {
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