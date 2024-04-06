package tech.lyuku.englishmanual.base.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class EMBasePageStateViewModel : EMBaseViewModel() {

    private val _pageState = MutableLiveData<PageState>()
    val pageState: LiveData<PageState> = _pageState

    fun setPageState(pageState: PageState) {
        _pageState.value = pageState
    }
}