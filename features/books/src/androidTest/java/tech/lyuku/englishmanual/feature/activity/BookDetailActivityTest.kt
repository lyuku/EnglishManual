package tech.lyuku.englishmanual.feature.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isNotSelected
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import tech.lyuku.englishmanual.base.core.base.DataResult
import tech.lyuku.englishmanual.books.R
import tech.lyuku.englishmanual.data.models.BookItem
import tech.lyuku.englishmanual.feature.books.data.db.IBookDao
import tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository
import tech.lyuku.englishmanual.feature.books.di.IBooksModule
import tech.lyuku.englishmanual.feature.books.ui.BookDetailActivity


@HiltAndroidTest
@UninstallModules(IBooksModule::class)
@RunWith(AndroidJUnit4::class)
class BookDetailActivityTest {

    private lateinit var ac: ActivityScenario<BookDetailActivity>

    private val fakeBookId = "fakeBookId"
    private val fakeAuthor = "fakeAuthor"
    private val fakePublisher = "fakePublisher"
    private val fakeNameBook = "fakeNameBook"
    private val fakeBookItem = BookItem(
        idBook = fakeBookId,
        author = fakeAuthor,
        publisher = fakePublisher,
        nameBook = fakeNameBook
    )

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val booksRepository: IBooksRepository = Mockito.mock(IBooksRepository::class.java)

    @BindValue
    @JvmField
    val booksDao: IBookDao = Mockito.mock(IBookDao::class.java)

    private fun startActivity() {
        val intent =
            BookDetailActivity.getStartIntent(getInstrumentation().targetContext, fakeBookItem)
        ac = ActivityScenario.launch(intent)
    }

    @After
    fun after() = runTest {
        ac.close()
    }

    @Test
    fun test_check_book_detail() = runTest {
        onView(withId(R.id.tv_book_title))
            .check(ViewAssertions.matches(withText(fakeBookItem.nameBook)))
        onView(withId(R.id.tv_author))
            .check(ViewAssertions.matches(withText(fakeBookItem.author)))
        onView(withId(R.id.tv_publisher))
            .check(ViewAssertions.matches(withText(fakeBookItem.publisher)))
    }

    @Test
    fun test_in_my_books() = runTest {
        Mockito.`when`(booksRepository.checkBookInMyBooks(fakeBookId))
            .thenReturn(DataResult.Success(true))
        startActivity()
        onView(withId(R.id.tv_my_books)).check(ViewAssertions.matches(isSelected()))
    }

    @Test
    fun test_not_in_my_books() = runTest {
        Mockito.`when`(booksRepository.checkBookInMyBooks(fakeBookId))
            .thenReturn(DataResult.Success(false))
        startActivity()
        onView(withId(R.id.tv_my_books)).check(ViewAssertions.matches(isNotSelected()))
    }

    @Test
    fun test_check_remove_from_my_books() = runTest {
        Mockito.`when`(booksRepository.checkBookInMyBooks(fakeBookId))
            .thenReturn(DataResult.Success(true))
        startActivity()
        onView(withId(R.id.tv_my_books)).perform(click())
        Mockito.verify(booksRepository).removeFromMyBooks(fakeBookId)
    }

    @Test
    fun test_check_add_to_my_books() = runTest {
        Mockito.`when`(booksRepository.checkBookInMyBooks(fakeBookId))
            .thenReturn(DataResult.Success(false))
        startActivity()
        onView(withId(R.id.tv_my_books)).perform(click())
        Mockito.verify(booksRepository).addToMyBooks(fakeBookId)
    }

}
