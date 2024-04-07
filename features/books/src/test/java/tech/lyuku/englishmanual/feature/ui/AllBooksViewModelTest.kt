package tech.lyuku.englishmanual.feature.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import tech.lyuku.englishmanual.base.core.base.DataResult
import tech.lyuku.englishmanual.base.core.base.PageState
import tech.lyuku.englishmanual.data.models.TopCategory
import tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository
import tech.lyuku.englishmanual.feature.books.ui.AllBooksViewModel
import tech.lyuku.englishmanual.feature.tools.MainCoroutineRule
import tech.lyuku.englishmanual.feature.tools.getOrAwaitValue

@ExperimentalCoroutinesApi
class AllBooksViewModelTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    val fakeRepo: IBooksRepository = mock(
        IBooksRepository::class.java
    )

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `check normal result`() = runTest {
        val allBooksViewModel =
            AllBooksViewModel(fakeRepo)
        val mockTopCategory = TopCategory()
        `when`(fakeRepo.getAllBooksCategory()).thenReturn(DataResult.Success(mockTopCategory))
        allBooksViewModel.loadBooks()
        advanceUntilIdle()
        val data = allBooksViewModel.allBooksCategoryList.getOrAwaitValue()
        assert(data == mockTopCategory)
        val state = allBooksViewModel.pageState.getOrAwaitValue()
        assert(state == PageState.Loaded)
    }

    @Test
    fun `check empty result`() = runTest {
        val allBooksViewModel =
            AllBooksViewModel(fakeRepo)
        `when`(fakeRepo.getAllBooksCategory()).thenReturn(DataResult.Success(null))
        allBooksViewModel.loadBooks()
        advanceUntilIdle()
        val state = allBooksViewModel.pageState.getOrAwaitValue()
        assert(state == PageState.Empty)
    }

    @Test
    fun `check error result`() = runTest {
        val allBooksViewModel =
            AllBooksViewModel(fakeRepo)
        val errorMsg = "error"
        `when`(fakeRepo.getAllBooksCategory()).thenReturn(DataResult.Error(Exception(errorMsg)))
        allBooksViewModel.loadBooks()
        advanceUntilIdle()
        val state = allBooksViewModel.pageState.getOrAwaitValue()
        assert(state == PageState.Error(errorMsg))
    }

}