package tech.lyuku.englishmanual.features.books.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.lyuku.englishmanual.core.base.DataResult
import tech.lyuku.englishmanual.core.base.PageState
import tech.lyuku.englishmanual.features.books.data.repository.IBooksRepository
import tech.lyuku.englishmanual.models.TopCategory
import javax.inject.Inject

@HiltViewModel
class AllBooksViewModel @Inject constructor(
    private val booksRepository: IBooksRepository
) : ViewModel() {

    private val _pageState = MutableLiveData<PageState>()
    val pageState: LiveData<PageState> = _pageState

    private val _allBooksCategoryList = MutableLiveData<TopCategory>()
    val allBooksCategoryList: LiveData<TopCategory> = _allBooksCategoryList

    fun loadBooks() {
        _pageState.value = PageState.Loading
        viewModelScope.launch {
            when (val result = booksRepository.getAllBooksCategory()) {
                is DataResult.Success -> {
                    if (result.data == null) {
                        _pageState.value = PageState.Empty
                    } else {
                        result.data.run {
                            _allBooksCategoryList.value = this
                        }
                        _pageState.value = PageState.Loaded
                    }
                }
                is DataResult.Error -> {
                    _pageState.value = PageState.Error(result.error.message.toString())
                }
            }
        }
    }
}