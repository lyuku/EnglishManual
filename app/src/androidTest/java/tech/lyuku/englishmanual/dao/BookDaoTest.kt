package tech.lyuku.englishmanual.dao

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.realm.Realm
import io.realm.RealmConfiguration
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tech.lyuku.englishmanual.core.db.IRealmProvider
import tech.lyuku.englishmanual.features.books.data.db.BookDao
import tech.lyuku.englishmanual.models.MyBookItem
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
class BookDaoTest {
    private lateinit var bookDao: BookDao
    private lateinit var realm: Realm
    private val realmDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    private val realmProvider = object : IRealmProvider {
        override suspend fun getRealm(): Realm = withContext(realmDispatcher) {
            Realm.init(ApplicationProvider.getApplicationContext())
            val config = RealmConfiguration.Builder()
                .inMemory() // create in memory
                .name("test-realm")
                .build()
            realm = Realm.getInstance(config)
            realm
        }
    }

    @Before
    fun setupRealm() = runBlocking {
        // init realm
        withContext(realmDispatcher) { realmProvider.getRealm() }
        // create bookDao
        bookDao = BookDao(realmProvider, realmDispatcher)
    }

    @After
    fun closeRealm() = runBlocking(realmDispatcher) {
        realm.close()
    }

    @Test
    fun testCreateBookItem() = runBlocking(realmDispatcher) {
        val bookItem = MyBookItem("fakeId", System.currentTimeMillis())
        bookDao.addToMyBooks(bookItem)
        realm.executeTransaction {
            assertNotNull(
                it.where(MyBookItem::class.java)
                    .equalTo("bookId", bookItem.bookId).findFirst()
            )
        }
    }

    @Test
    fun testFindBookItemById() = runBlocking(realmDispatcher) {
        val bookItem = MyBookItem("fakeId", System.currentTimeMillis())
        realm.executeTransaction {
            it.insert(bookItem)
        }
        assertNotNull(bookDao.findMyBookByBookId(bookItem.bookId))
    }

    @Test
    fun testRemoveBookItem() = runBlocking(realmDispatcher) {
        val bookItem = MyBookItem("fakeId", System.currentTimeMillis())
        realm.executeTransaction {
            it.insert(bookItem)
        }
        assertNotNull(
            realm.where(MyBookItem::class.java)
                .equalTo("bookId", bookItem.bookId).findFirst()
        )
        bookDao.removeFromMyBooks(bookItem.bookId)
        assertNull(
            realm.where(MyBookItem::class.java)
                .equalTo("bookId", bookItem.bookId).findFirst()
        )
    }
}
