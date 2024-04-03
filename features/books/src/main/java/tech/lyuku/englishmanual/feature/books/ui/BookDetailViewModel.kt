package tech.lyuku.englishmanual.feature.books.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import tech.lyuku.englishmanual.base.core.base.DataResult
import tech.lyuku.englishmanual.base.core.utils.SingleLiveEvent
import tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository
import tech.lyuku.englishmanual.data.models.BookItem
import tech.lyuku.englishmanual.feature.books.data.BookDetailAction
import javax.inject.Inject

/**
 * ViewModel for [BookDetailActivity]
 */
@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val booksRepository: IBooksRepository
) : ViewModel() {

    lateinit var book: BookItem
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

    fun onChangeIsMyBook() {
        if (isMyBook.value == true) {
            removeFromMyBooks()
        } else {
            addToMyBooks()
        }
    }

    @VisibleForTesting
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
