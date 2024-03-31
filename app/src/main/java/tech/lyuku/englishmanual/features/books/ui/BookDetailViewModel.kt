package tech.lyuku.englishmanual.features.books.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.lyuku.englishmanual.core.base.DataResult
import tech.lyuku.englishmanual.core.utils.SingleLiveEvent
import tech.lyuku.englishmanual.features.books.data.repository.IBooksRepository
import tech.lyuku.englishmanual.models.BookDetailAction
import tech.lyuku.englishmanual.models.BookItem
import javax.inject.Inject

/**
 * ViewModel for [BookDetailActivity]
 */
@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val booksRepository: IBooksRepository
) : ViewModel() {

    lateinit var book: BookItem
    val closeActivity = SingleLiveEvent<Boolean>()
    val showMessageEvent = SingleLiveEvent<String>()
    val actions = BookDetailAction.entries

    private val _isMyBook = MutableLiveData<Boolean>()
    val isMyBook: LiveData<Boolean> = _isMyBook

    fun init(bookItem: BookItem) {
        book = bookItem
        viewModelScope.launch {
            book.idBook?.run {
                val result = booksRepository.checkBookInMyBooks(this)
                if (result is DataResult.Success) {
                    _isMyBook.value = result.data ?: false
                } else if (result is DataResult.Error) {
                    showMessageEvent.value = result.error.message
                }
            }
        }
    }

    fun onBackClicked() {
        closeActivity.value = true
    }

    fun onChangeIsMyBook() {
        if (isMyBook.value == true) {
            removeFromMyBooks()
        } else {
            addToMyBooks()
        }
    }

    private fun removeFromMyBooks() {
        viewModelScope.launch {
            book.idBook?.run {
                val result = booksRepository.removeFromMyBooks(this)
                if (result is DataResult.Success) {
                    _isMyBook.value = false
                } else if (result is DataResult.Error) {
                    showMessageEvent.value = result.error.message
                }
            }
        }
    }

    private fun addToMyBooks() {
        viewModelScope.launch {
            book.idBook?.run {
                val result = booksRepository.addToMyBooks(this)
                if (result is DataResult.Success) {
                    _isMyBook.value = true
                } else if (result is DataResult.Error) {
                    showMessageEvent.value = result.error.message
                }
            }
        }
    }

    fun freeReading() {
        showMessageEvent.value = "Not yet implemented"
    }
}
