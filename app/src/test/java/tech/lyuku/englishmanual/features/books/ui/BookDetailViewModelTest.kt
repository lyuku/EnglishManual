@file:OptIn(ExperimentalCoroutinesApi::class)

package tech.lyuku.englishmanual.features.books.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import tech.lyuku.englishmanual.core.base.DataResult
import tech.lyuku.englishmanual.features.books.data.repository.IBooksRepository
import tech.lyuku.englishmanual.models.BookItem
import tech.lyuku.englishmanual.tools.MainCoroutineRule
import tech.lyuku.englishmanual.tools.getOrAwaitValue

class BookDetailViewModelTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val fakeBookId = "fakeBookId"
    private val fakeBookItem = BookItem(idBook = fakeBookId)

    @Test
    fun `check init is my book`() = runTest {

        val fakeRepo = mock(IBooksRepository::class.java).apply {
            `when`(this.checkBookInMyBooks(fakeBookId)).thenReturn(DataResult.Success(true))
        }
        val bookDetailViewModel = BookDetailViewModel(fakeRepo)
        bookDetailViewModel.init(fakeBookItem)
        advanceUntilIdle()
        assert(bookDetailViewModel.isMyBook.getOrAwaitValue())
    }

    @Test
    fun `check init not my book`() = runTest {
        val fakeRepo = mock(IBooksRepository::class.java).apply {
            `when`(this.checkBookInMyBooks(fakeBookId)).thenReturn(DataResult.Success(false))
        }
        val bookDetailViewModel = BookDetailViewModel(fakeRepo)
        bookDetailViewModel.init(fakeBookItem)
        advanceUntilIdle()
        assert(!bookDetailViewModel.isMyBook.getOrAwaitValue())
    }

    @Test
    fun `check change is my book from true to false`() = runTest {
        val fakeRepo = mock(IBooksRepository::class.java).apply {
            `when`(this.checkBookInMyBooks(fakeBookId)).thenReturn(DataResult.Success(true))
            `when`(this.removeFromMyBooks(fakeBookId)).thenReturn(DataResult.Success(true))
        }
        val bookDetailViewModel = BookDetailViewModel(fakeRepo)
        bookDetailViewModel.init(fakeBookItem)
        advanceUntilIdle()
        bookDetailViewModel.onChangeIsMyBook()
        advanceUntilIdle()
        assert(!bookDetailViewModel.isMyBook.getOrAwaitValue())
    }

    @Test
    fun `check change is my book from false to true`() = runTest {
        val fakeRepo = mock(IBooksRepository::class.java).apply {
            `when`(this.checkBookInMyBooks(fakeBookId)).thenReturn(DataResult.Success(false))
            `when`(this.addToMyBooks(fakeBookId)).thenReturn(DataResult.Success(true))
        }
        val bookDetailViewModel = BookDetailViewModel(fakeRepo)
        bookDetailViewModel.init(fakeBookItem)
        advanceUntilIdle()
        bookDetailViewModel.onChangeIsMyBook()
        advanceUntilIdle()
        assert(bookDetailViewModel.isMyBook.getOrAwaitValue())
    }
}