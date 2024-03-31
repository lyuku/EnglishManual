package tech.lyuku.englishmanual

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInit()
    }

    private fun appInit() {
        // init realm
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("english_manual.db")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}