package tech.lyuku.englishmanual.feature.books.data.db

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import tech.lyuku.englishmanual.base.core.db.IRealmProvider
import tech.lyuku.englishmanual.base.di.RealmDispatcher
import tech.lyuku.englishmanual.data.models.MyBookItem
import javax.inject.Inject

class BookDao @Inject constructor(
    private val realmProvider: IRealmProvider,
    @RealmDispatcher private val realmDispatcher: CoroutineDispatcher
) : IBookDao {

    override suspend fun findMyBookByBookId(bookId: String): MyBookItem? =
        withContext(realmDispatcher) {
            val realm = realmProvider.getRealm()
            realm.where(MyBookItem::class.java)
                .equalTo("bookId", bookId).findFirst()
        }

    override suspend fun addToMyBooks(myBookItem: MyBookItem) = withContext(realmDispatcher) {
        realmProvider.getRealm().executeTransaction {
            it.insertOrUpdate(myBookItem)
        }
    }


    override suspend fun removeFromMyBooks(bookId: String) = withContext(realmDispatcher) {
        realmProvider.getRealm().executeTransaction {
            it.where(MyBookItem::class.java).equalTo("bookId", bookId)
                .findFirst()?.deleteFromRealm()
        }
    }
}