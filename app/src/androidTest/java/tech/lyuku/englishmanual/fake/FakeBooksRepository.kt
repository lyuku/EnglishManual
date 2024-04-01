package tech.lyuku.englishmanual.fake

import tech.lyuku.englishmanual.core.base.DataResult
import tech.lyuku.englishmanual.features.books.data.repository.IBooksRepository
import tech.lyuku.englishmanual.models.TopCategory
import javax.inject.Inject

class FakeBooksRepository @Inject constructor() : IBooksRepository {

    companion object {
        const val IN_MY_BOOKS_ID = "in_my_books"
        const val NOT_IN_MY_BOOKS_ID = "not_in_my_books"
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