package tech.lyuku.englishmanual.feature.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import tech.lyuku.englishmanual.base.core.base.DataResult
import tech.lyuku.englishmanual.books.R
import tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository
import tech.lyuku.englishmanual.feature.books.di.IBooksModule
import tech.lyuku.englishmanual.feature.books.ui.AllBooksActivity
import tech.lyuku.englishmanual.feature.books.ui.BookDetailActivity
import tech.lyuku.englishmanual.feature.fake.FakeBooksRepository
import tech.lyuku.englishmanual.feature.fake.PageStateObserverImpl
import tech.lyuku.englishmanual.feature.tools.withIndex


@HiltAndroidTest
@UninstallModules(IBooksModule::class)
@RunWith(AndroidJUnit4::class)
class AllBooksActivityTest {

    private lateinit var activityScenario: ActivityScenario<AllBooksActivity>

    @BindValue
    @JvmField
    val booksRepository: IBooksRepository = mock(IBooksRepository::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun before() = runTest {

    }

    @After
    fun after() = runTest {
        activityScenario.close()
    }

    private fun launchActivity() = runTest {
        activityScenario = ActivityScenario.launch(AllBooksActivity::class.java)
        activityScenario.onActivity { activity ->
            println("activityScenario.onActivity")
            val observerImpl = (activity as AllBooksActivity).stateObserver as PageStateObserverImpl
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(observerImpl.getIdlingResource())
        }
    }

    @Test
    fun test_load_data_success() = runTest {
        val mockResult = DataResult.Success(FakeBooksRepository.FAKE_DATA)
        Mockito.`when`(booksRepository.getAllBooksCategory()).thenReturn(mockResult)
        launchActivity()

        onView(withText(FakeBooksRepository.SUB_CATEGORY_1)).check(matches(isDisplayed()))
    }

    @Test
    fun test_click_book_item() = runTest {
        val mockResult = DataResult.Success(FakeBooksRepository.FAKE_DATA)
        Mockito.`when`(booksRepository.getAllBooksCategory()).thenReturn(mockResult)
        launchActivity()

        Intents.init()

        onView(withIndex(withId(R.id.cl_book), 0)).perform(click())

        Intents.intended(
            CoreMatchers.allOf(
                hasComponent(BookDetailActivity::class.java.name),
                hasExtra(BookDetailActivity.EXTRA_KEY_BOOK_ITEM, FakeBooksRepository.FAKE_BOOK_1)
            )
        )
        Intents.release()
    }

    @Test
    fun test_load_data_success_empty() = runTest {
        Mockito.`when`(booksRepository.getAllBooksCategory()).thenReturn(
            DataResult.Success(null)
        )
        launchActivity()

        onView(withId(tech.lyuku.englishmanual.base.R.id.iv_empty)).check(matches(isDisplayed()))
    }

    @Test
    fun test_load_data_error() = runTest {
        val errorMessage = "some error message"
        Mockito.`when`(booksRepository.getAllBooksCategory()).thenReturn(
            DataResult.Error(Exception(errorMessage))
        )
        launchActivity()

        onView(withId(tech.lyuku.englishmanual.base.R.id.iv_error)).check(matches(isDisplayed()))
        onView(withText(errorMessage)).check(matches(isDisplayed()))
    }

}