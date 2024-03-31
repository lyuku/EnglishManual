package tech.lyuku.englishmanual.features.books.data.db

import io.realm.Realm
import tech.lyuku.englishmanual.models.MyBookItem
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class BookDao @Inject constructor(
    private val db: Realm
) : IBookDao {

    override suspend fun findMyBookByBookId(bookId: String): MyBookItem? =
        suspendCoroutine { cont ->
            db.executeTransactionAsync({
                val myBookItem = it.where(MyBookItem::class.java)
                    .equalTo("bookId", bookId).findFirst()
                cont.resume(myBookItem)
            }, { e ->
                cont.resumeWithException(e)
            })
        }

    override suspend fun addToMyBooks(myBookItem: MyBookItem) = suspendCoroutine { cont ->
        db.executeTransactionAsync({
            it.insertOrUpdate(myBookItem)
            cont.resumeWith(Result.success(Unit))
        }, { e ->
            cont.resumeWithException(e)
        })
    }

    override suspend fun removeFromMyBooks(bookId: String) = suspendCoroutine { cont ->
        db.executeTransactionAsync({
            it.where(MyBookItem::class.java).equalTo("bookId", bookId)
                .findFirst()?.deleteFromRealm()
            cont.resumeWith(Result.success(Unit))
        }, { e ->
            cont.resumeWithException(e)
        })
    }
}