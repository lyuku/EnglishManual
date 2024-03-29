package tech.lyuku.englishmanual.features.books.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.lyuku.englishmanual.core.base.DataResult
import tech.lyuku.englishmanual.core.base.PageStatue
import tech.lyuku.englishmanual.features.books.data.repository.IBooksRepository
import tech.lyuku.englishmanual.models.TopCategory
import javax.inject.Inject

@HiltViewModel
class AllBooksViewModel @Inject constructor(
    private val booksRepository: IBooksRepository
) : ViewModel() {

    private val _pageStatue = MutableLiveData<PageStatue>()
    val pageStatue: LiveData<PageStatue> = _pageStatue

    private val _allBooksCategoryList = MutableLiveData<TopCategory>()
    val allBooksCategoryList: LiveData<TopCategory> = _allBooksCategoryList

    fun loadBooks() {
        _pageStatue.value = PageStatue.Loading
        viewModelScope.launch {
            when (val result = booksRepository.getBooksGroupedByCategory()) {
                is DataResult.Success -> {
                    if (result.data == null) {
                        _pageStatue.value = PageStatue.Empty
                    } else {
                        result.data.run {
                            _allBooksCategoryList.value = this
                        }
                        _pageStatue.value = PageStatue.Loaded
                    }
                }
                is DataResult.Error -> {
                    _pageStatue.value = PageStatue.Error(result.error.message.toString())
                }
            }
        }
    }
}