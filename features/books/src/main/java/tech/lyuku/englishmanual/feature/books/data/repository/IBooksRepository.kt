package tech.lyuku.englishmanual.feature.books.data.repository

import tech.lyuku.englishmanual.base.core.base.DataResult
import tech.lyuku.englishmanual.data.models.TopCategory

interface IBooksRepository {

    /**
     * Get all books category
     */
    suspend fun getAllBooksCategory(): DataResult<TopCategory>

    /**
     * Check if book is in MyBooks
     */
    suspend fun checkBookInMyBooks(bookId: String): DataResult<Boolean>

    /**
     * Add book to MyBooks
     */
    suspend fun addToMyBooks(bookId: String): DataResult<Any>

    /**
     * Remove book from MyBooks
     */
    suspend fun removeFromMyBooks(bookId: String): DataResult<Any>
}
