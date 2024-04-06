package tech.lyuku.englishmanual.feature.fake

import tech.lyuku.englishmanual.base.core.base.DataResult
import tech.lyuku.englishmanual.data.models.BookItem
import tech.lyuku.englishmanual.data.models.SubCategory
import tech.lyuku.englishmanual.data.models.TopCategory
import javax.inject.Inject

class FakeBooksRepository @Inject constructor() :
    tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository {

    companion object {
        const val IN_MY_BOOKS_ID = "in_my_books"
        const val NOT_IN_MY_BOOKS_ID = "not_in_my_books"
        const val SUB_CATEGORY_1 = "Sub Category 1"

        val FAKE_BOOK_1 = BookItem(idBook = IN_MY_BOOKS_ID, nameBook = "Book 1")

        val FAKE_DATA = TopCategory(
            nameCategory = "Top Category",
            subCategoryList = listOf(
                SubCategory(
                    nameCategory = "Sub Category 1",
                    bookList = listOf(
                        FAKE_BOOK_1,
                        BookItem(idBook = NOT_IN_MY_BOOKS_ID, nameBook = "Book 2"),
                    )
                ),
                SubCategory(
                    nameCategory = "Sub Category 2",
                    bookList = listOf(
                        BookItem(idBook = IN_MY_BOOKS_ID, nameBook = "Book 1"),
                        BookItem(idBook = NOT_IN_MY_BOOKS_ID, nameBook = "Book 2"),
                    )
                ),
            )
        )
    }

    override suspend fun getAllBooksCategory(): DataResult<TopCategory> {
        return DataResult.Success(TopCategory())
    }

    override suspend fun checkBookInMyBooks(bookId: String): DataResult<Boolean> {
        return DataResult.Success(IN_MY_BOOKS_ID == bookId)
    }

    override suspend fun addToMyBooks(bookId: String): DataResult<Any> {
        return DataResult.Success(Unit)
    }

    override suspend fun removeFromMyBooks(bookId: String): DataResult<Any> {
        return DataResult.Success(Unit)
    }
}