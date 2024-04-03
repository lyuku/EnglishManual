package tech.lyuku.englishmanual.base.core.db

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RealmProvider(
    private val context: Context,
    private val realmDispatcher: CoroutineDispatcher
) : IRealmProvider {

    private lateinit var realm: Realm

    override suspend fun getRealm(): Realm = withContext(realmDispatcher) {
        if (!this@RealmProvider::realm.isInitialized) {
            realmConfig()
            realm = Realm.getDefaultInstance()
        }
        realm
    }

    private fun realmConfig() {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .name("english_manual.db")
            .allowQueriesOnUiThread(false)
            .allowWritesOnUiThread(false)
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}
