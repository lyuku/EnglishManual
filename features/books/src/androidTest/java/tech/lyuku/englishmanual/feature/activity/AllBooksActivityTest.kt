package tech.lyuku.englishmanual.feature.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tech.lyuku.englishmanual.books.R
import tech.lyuku.englishmanual.feature.books.ui.AllBooksActivity
import tech.lyuku.englishmanual.feature.fake.PageStateObserverImpl

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AllBooksActivityTest {

    private lateinit var activityScenario: ActivityScenario<AllBooksActivity>
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun before() = runTest {

        activityScenario = ActivityScenario.launch(AllBooksActivity::class.java)
        activityScenario.onActivity { activity ->
            println("activityScenario.onActivity")
            val observerImpl = (activity as AllBooksActivity).stateObserver as PageStateObserverImpl
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(observerImpl.getIdlingResource())
        }
    }

    @After
    fun after() = runTest {
        activityScenario.close()
    }

    @Test
    fun on_start() = runTest {
        onView(withId(R.id.rv_book_category)).check(matches(isDisplayed()))
    }

}