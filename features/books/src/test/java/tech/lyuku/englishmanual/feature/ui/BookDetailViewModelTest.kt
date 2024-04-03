@file:OptIn(ExperimentalCoroutinesApi::class)

package tech.lyuku.englishmanual.feature.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import tech.lyuku.englishmanual.feature.tools.MainCoroutineRule
import tech.lyuku.englishmanual.feature.tools.getOrAwaitValue

class BookDetailViewModelTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val fakeBookId = "fakeBookId"
    private val fakeBookItem = tech.lyuku.englishmanual.data.models.BookItem(idBook = fakeBookId)

    @Test
    fun `check init is my book`() = runTest {

        val fakeRepo = mock(tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository::class.java).apply {
            `when`(this.checkBookInMyBooks(fakeBookId)).thenReturn(tech.lyuku.englishmanual.base.core.base.DataResult.Success(true))
        }
        val bookDetailViewModel =
            tech.lyuku.englishmanual.feature.books.ui.BookDetailViewModel(fakeRepo)
        bookDetailViewModel.init(fakeBookItem)
        advanceUntilIdle()
        assert(bookDetailViewModel.isMyBook.getOrAwaitValue())
    }

    @Test
    fun `check init not my book`() = runTest {
        val fakeRepo = mock(tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository::class.java).apply {
            `when`(this.checkBookInMyBooks(fakeBookId)).thenReturn(tech.lyuku.englishmanual.base.core.base.DataResult.Success(false))
        }
        val bookDetailViewModel =
            tech.lyuku.englishmanual.feature.books.ui.BookDetailViewModel(fakeRepo)
        bookDetailViewModel.init(fakeBookItem)
        advanceUntilIdle()
        assert(!bookDetailViewModel.isMyBook.getOrAwaitValue())
    }

    @Test
    fun `check change is my book from true to false`() = runTest {
        val fakeRepo = mock(tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository::class.java).apply {
            `when`(this.checkBookInMyBooks(fakeBookId)).thenReturn(tech.lyuku.englishmanual.base.core.base.DataResult.Success(true))
            `when`(this.removeFromMyBooks(fakeBookId)).thenReturn(tech.lyuku.englishmanual.base.core.base.DataResult.Success(true))
        }
        val bookDetailViewModel =
            tech.lyuku.englishmanual.feature.books.ui.BookDetailViewModel(fakeRepo)
        bookDetailViewModel.init(fakeBookItem)
        advanceUntilIdle()
        bookDetailViewModel.onChangeIsMyBook()
        advanceUntilIdle()
        assert(!bookDetailViewModel.isMyBook.getOrAwaitValue())
    }

    @Test
    fun `check change is my book from false to true`() = runTest {
        val fakeRepo = mock(tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository::class.java).apply {
            `when`(this.checkBookInMyBooks(fakeBookId)).thenReturn(tech.lyuku.englishmanual.base.core.base.DataResult.Success(false))
            `when`(this.addToMyBooks(fakeBookId)).thenReturn(tech.lyuku.englishmanual.base.core.base.DataResult.Success(true))
        }
        val bookDetailViewModel =
            tech.lyuku.englishmanual.feature.books.ui.BookDetailViewModel(fakeRepo)
        bookDetailViewModel.init(fakeBookItem)
        advanceUntilIdle()
        bookDetailViewModel.onChangeIsMyBook()
        advanceUntilIdle()
        assert(bookDetailViewModel.isMyBook.getOrAwaitValue())
    }
}