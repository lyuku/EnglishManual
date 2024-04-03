package tech.lyuku.englishmanual.base.core.db

import io.realm.Realm

interface IRealmProvider {

    suspend fun getRealm(): Realm
}
