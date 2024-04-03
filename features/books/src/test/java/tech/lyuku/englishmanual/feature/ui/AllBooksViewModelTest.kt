package tech.lyuku.englishmanual.feature.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import tech.lyuku.englishmanual.feature.tools.MainCoroutineRule
import tech.lyuku.englishmanual.feature.tools.getOrAwaitValue

@ExperimentalCoroutinesApi
class AllBooksViewModelTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    val fakeRepo: tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository = mock(
        tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository::class.java)

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `check normal result`() = runTest {
        val allBooksViewModel =
            tech.lyuku.englishmanual.feature.books.ui.AllBooksViewModel(fakeRepo)
        val mockTopCategory = tech.lyuku.englishmanual.data.models.TopCategory()
        `when`(fakeRepo.getAllBooksCategory()).thenReturn(tech.lyuku.englishmanual.base.core.base.DataResult.Success(mockTopCategory))
        allBooksViewModel.loadBooks()
        advanceUntilIdle()
        val data = allBooksViewModel.allBooksCategoryList.getOrAwaitValue()
        assert(data == mockTopCategory)
        val state = allBooksViewModel.pageState.getOrAwaitValue()
        assert(state == tech.lyuku.englishmanual.base.core.base.PageState.Loaded)
    }

    @Test
    fun `check empty result`() = runTest {
        val allBooksViewModel =
            tech.lyuku.englishmanual.feature.books.ui.AllBooksViewModel(fakeRepo)
        `when`(fakeRepo.getAllBooksCategory()).thenReturn(tech.lyuku.englishmanual.base.core.base.DataResult.Success(null))
        allBooksViewModel.loadBooks()
        advanceUntilIdle()
        val state = allBooksViewModel.pageState.getOrAwaitValue()
        assert(state == tech.lyuku.englishmanual.base.core.base.PageState.Empty)
    }

    @Test
    fun `check error result`() = runTest {
        val allBooksViewModel =
            tech.lyuku.englishmanual.feature.books.ui.AllBooksViewModel(fakeRepo)
        val errorMsg = "error"
        `when`(fakeRepo.getAllBooksCategory()).thenReturn(tech.lyuku.englishmanual.base.core.base.DataResult.Error(Exception(errorMsg)))
        allBooksViewModel.loadBooks()
        advanceUntilIdle()
        val state = allBooksViewModel.pageState.getOrAwaitValue()
        assert(state == tech.lyuku.englishmanual.base.core.base.PageState.Error(errorMsg))
    }

}