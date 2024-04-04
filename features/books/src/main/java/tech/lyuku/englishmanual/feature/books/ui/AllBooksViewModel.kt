package tech.lyuku.englishmanual.feature.books.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.lyuku.englishmanual.base.core.base.DataResult
import tech.lyuku.englishmanual.base.core.base.EMBasePageStateViewModel
import tech.lyuku.englishmanual.base.core.base.PageState
import tech.lyuku.englishmanual.data.models.TopCategory
import tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository
import javax.inject.Inject

@HiltViewModel
class AllBooksViewModel @Inject constructor(
    private val booksRepository: IBooksRepository
) : EMBasePageStateViewModel() {

    private val _allBooksCategoryList = MutableLiveData<TopCategory>()
    val allBooksCategoryList: LiveData<TopCategory> = _allBooksCategoryList

    fun loadBooks() {
        super.setPageState(PageState.Loading)
        viewModelScope.launch {
            when (val result = booksRepository.getAllBooksCategory()) {
                is DataResult.Success -> {
                    if (result.data == null) {
                        super.setPageState(PageState.Empty)
                    } else {
                        result.data.run {
                            _allBooksCategoryList.value = this
                        }
                        super.setPageState(PageState.Loaded)
                    }
                }
                is DataResult.Error -> {
                    super.setPageState(PageState.Error(result.error.message.toString()))
                }
            }
        }
    }
}