package tech.lyuku.englishmanual.features.books.data.db

import tech.lyuku.englishmanual.models.MyBookItem

interface IBookDao {

    /**
     * find MyBookItem by bookId
     * if not found, return null
     */
    suspend fun findMyBookByBookId(bookId: String): MyBookItem?

    /**
     * save MyBookItem
     */
    suspend fun addToMyBooks(myBookItem: MyBookItem)

    /**
     * remove MyBookItem
     */
    suspend fun removeFromMyBooks(bookId: String)
}