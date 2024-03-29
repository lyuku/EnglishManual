package tech.lyuku.englishmanual.features.books.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.lyuku.englishmanual.core.utils.SingleLiveEvent
import tech.lyuku.englishmanual.models.BookItem
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
) : ViewModel() {

    var book: BookItem? = null
    val closeActivity = SingleLiveEvent<Boolean>()

    fun init(bookItem: BookItem?) {
        book = bookItem
    }

    fun onBackClicked() {
        closeActivity.value = true
    }

    fun addToMyBooks() {
        // TODO
    }

    fun freeReading() {
        // TODO
    }
}
