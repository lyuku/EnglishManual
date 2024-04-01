package tech.lyuku.englishmanual.activity

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tech.lyuku.englishmanual.R
import tech.lyuku.englishmanual.features.books.ui.AllBooksActivity

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AllBooksActivityTest {

    private lateinit var ac: ActivityScenario<AllBooksActivity>

    @Before
    fun before() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val allBooksIntent = Intent(context, AllBooksActivity::class.java)
        ac = ActivityScenario.launch(allBooksIntent)
    }

    @After
    fun after() = runTest {
        ac.close()
    }

    @Test
    fun on_start() = runTest {
        onView(withId(R.id.rv_book_category)).check(matches(isDisplayed()))
    }

}