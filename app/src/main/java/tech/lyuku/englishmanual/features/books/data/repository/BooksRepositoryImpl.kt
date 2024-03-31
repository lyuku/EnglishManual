package tech.lyuku.englishmanual.features.books.data.repository

import tech.lyuku.englishmanual.core.base.DataResult
import tech.lyuku.englishmanual.features.books.data.db.IBookDao
import tech.lyuku.englishmanual.features.books.data.net.IBookApi
import tech.lyuku.englishmanual.models.MyBookItem
import tech.lyuku.englishmanual.models.TopCategory
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val bookApi: IBookApi,
    private val bookDao: IBookDao
) : IBooksRepository {

    companion object {
        const val TOP_CATEGORY_ALL_ID = "_top"
    }

    override suspend fun getBooksGroupedByCategory(): DataResult<TopCategory> {
        val response = bookApi.getBooksGroupedByCategory()
        if (response.isSuccessful) {
            val result = response.body()
            result?.topCategoryList?.forEach {
                if (it.idTopCategory == TOP_CATEGORY_ALL_ID) {
                    return DataResult.Success(it)
                }
            }
            return DataResult.Error(Exception("No all category found"))
        } else {
            return DataResult.Error(Exception(response.message()))
        }
    }

    override suspend fun checkBookInMyBooks(bookId: String): DataResult<Boolean> {
        return try {
            val myBookItem = bookDao.findMyBookByBookId(bookId)
            DataResult.Success(myBookItem != null)
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun addToMyBooks(bookId: String): DataResult<Any> {
        return try {
            bookDao.addToMyBooks(MyBookItem(bookId = bookId, time = System.currentTimeMillis()))
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun removeFromMyBooks(bookId: String): DataResult<Any> {
        return try {
            bookDao.removeFromMyBooks(bookId)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}