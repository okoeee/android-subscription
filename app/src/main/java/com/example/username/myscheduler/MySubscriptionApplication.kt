package com.example.username.myscheduler

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MySubscriptionApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Realm.init(applicationContext)
        val realmConfig = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.deleteRealm(realmConfig)
        Realm.setDefaultConfiguration(realmConfig)
    }
}