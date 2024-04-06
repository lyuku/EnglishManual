package tech.lyuku.englishmanual.feature.fake

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import tech.lyuku.englishmanual.base.core.base.PageState
import tech.lyuku.englishmanual.base.core.base.PageStateObserver

class PageStateObserverImpl : PageStateObserver {

    private val countingIdlingResource = CountingIdlingResource("PageStateIdlingResource")

    fun getIdlingResource(): IdlingResource = countingIdlingResource

    init {
        // since we mock repository, the loading state may not be triggered,
        // so we need to manually increment
        countingIdlingResource.increment()
    }

    override fun onPageStateChange(state: PageState) {
        println("onPageStateChange: $state")
        if (state == PageState.Loaded ||
            state is PageState.Error ||
            state == PageState.Empty
        ) {
            countingIdlingResource.decrement()
        }
    }
}