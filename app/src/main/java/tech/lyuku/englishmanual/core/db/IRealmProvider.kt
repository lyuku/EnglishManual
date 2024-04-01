package tech.lyuku.englishmanual.core.db

import io.realm.Realm

interface IRealmProvider {

    suspend fun getRealm(): Realm
}
