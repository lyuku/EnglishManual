package tech.lyuku.englishmanual.feature.fake

import javax.inject.Inject

class FakeBooksRepository @Inject constructor() :
    tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository {

    companion object {
        const val IN_MY_BOOKS_ID = "in_my_books"
        const val NOT_IN_MY_BOOKS_ID = "not_in_my_books"
    }

    override suspend fun getAllBooksCategory(): tech.lyuku.englishmanual.base.core.base.DataResult<tech.lyuku.englishmanual.data.models.TopCategory> {
        return tech.lyuku.englishmanual.base.core.base.DataResult.Success(tech.lyuku.englishmanual.data.models.TopCategory())
    }

    override suspend fun checkBookInMyBooks(bookId: String): tech.lyuku.englishmanual.base.core.base.DataResult<Boolean> {
        return tech.lyuku.englishmanual.base.core.base.DataResult.Success(IN_MY_BOOKS_ID == bookId)
    }

    override suspend fun addToMyBooks(bookId: String): tech.lyuku.englishmanual.base.core.base.DataResult<Any> {
        return tech.lyuku.englishmanual.base.core.base.DataResult.Success(Unit)
    }

    override suspend fun removeFromMyBooks(bookId: String): tech.lyuku.englishmanual.base.core.base.DataResult<Any> {
        return tech.lyuku.englishmanual.base.core.base.DataResult.Success(Unit)
    }
}