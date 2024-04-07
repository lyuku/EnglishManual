package tech.lyuku.englishmanual.feature.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response
import tech.lyuku.englishmanual.base.core.base.DataResult
import tech.lyuku.englishmanual.data.models.BookCategoryList
import tech.lyuku.englishmanual.data.models.MyBookItem
import tech.lyuku.englishmanual.data.models.TopCategory
import tech.lyuku.englishmanual.feature.books.data.api.IBookApi
import tech.lyuku.englishmanual.feature.books.data.db.IBookDao
import tech.lyuku.englishmanual.feature.books.data.repository.BooksRepositoryImpl
import tech.lyuku.englishmanual.feature.books.data.repository.BooksRepositoryImpl.Companion.TOP_CATEGORY_ALL_ID

class BooksRepositoryTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    val bookApi: IBookApi = Mockito.mock(IBookApi::class.java)

    @Mock
    val bookDao: IBookDao = Mockito.mock(IBookDao::class.java)

    private val bookRepo = BooksRepositoryImpl(bookApi, bookDao, Dispatchers.IO)

    private val mockBookCategoryList = BookCategoryList(
        topCategoryList = listOf(
            TopCategory(idTopCategory = TOP_CATEGORY_ALL_ID)
        )
    )

    @Test
    fun `check get all books category success`() = runTest {
        Mockito.`when`(bookApi.getBooksGroupedByCategory()).thenReturn(
            Response.success(mockBookCategoryList)
        )
        val result = bookRepo.getAllBooksCategory()
        assert(result is DataResult.Success)
        assert((result as DataResult.Success).data == mockBookCategoryList.topCategoryList?.get(0))
    }

    @Test
    fun `check get all books category empty`() = runTest {
        Mockito.`when`(bookApi.getBooksGroupedByCategory()).thenReturn(
            Response.success(BookCategoryList())
        )
        val result = bookRepo.getAllBooksCategory()
        assert(result is DataResult.Success)
        assert((result as DataResult.Success).data == null)
    }

    @Test
    fun `check get all books category error`() = runTest {
        Mockito.`when`(bookApi.getBooksGroupedByCategory()).thenReturn(
            Response.error(500, "error".toResponseBody(null))
        )
        val result = bookRepo.getAllBooksCategory()
        assert(result is DataResult.Error)
    }

    @Test
    fun `check book in my books`() = runTest {
        val fakeBookId = "fakeBookId"
        Mockito.`when`(bookDao.findMyBookByBookId(fakeBookId)).thenReturn(
            MyBookItem(fakeBookId, 0)
        )
        val result = bookRepo.checkBookInMyBooks(fakeBookId)
        assert(result is DataResult.Success)
        assert((result as DataResult.Success).data == true)
    }

    @Test
    fun `check book not in my books`() = runTest {
        val fakeBookId = "fakeBookId"
        Mockito.`when`(bookDao.findMyBookByBookId(fakeBookId)).thenReturn(
            null
        )
        val result = bookRepo.checkBookInMyBooks(fakeBookId)
        assert(result is DataResult.Success)
        assert((result as DataResult.Success).data == false)
    }

    @Test
    fun `check add to my books`() = runTest {
        val fakeBookId = "fakeBookId"
        val result = bookRepo.addToMyBooks(fakeBookId)
        assert(result is DataResult.Success)
    }

    @Test
    fun `check remove from my books`() = runTest {
        val fakeBookId = "fakeBookId"
        val result = bookRepo.removeFromMyBooks(fakeBookId)
        Mockito.verify(bookDao).removeFromMyBooks(fakeBookId)
        assert(result is DataResult.Success)
    }


}