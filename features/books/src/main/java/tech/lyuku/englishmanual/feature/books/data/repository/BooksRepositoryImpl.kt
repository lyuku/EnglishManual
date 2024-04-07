package tech.lyuku.englishmanual.feature.books.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import tech.lyuku.englishmanual.base.core.base.DataResult
import tech.lyuku.englishmanual.base.di.IoDispatcher
import tech.lyuku.englishmanual.feature.books.data.db.IBookDao
import tech.lyuku.englishmanual.feature.books.data.api.IBookApi
import tech.lyuku.englishmanual.data.models.MyBookItem
import tech.lyuku.englishmanual.data.models.TopCategory
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val bookApi: IBookApi,
    private val bookDao: IBookDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IBooksRepository {

    companion object {
        const val TOP_CATEGORY_ALL_ID = "_top"
    }

    override suspend fun getAllBooksCategory(): DataResult<TopCategory> =
        withContext(ioDispatcher) {
            val response = try {
                bookApi.getBooksGroupedByCategory()
            } catch (e: Exception) {
                return@withContext DataResult.Error(e)
            }
            if (response.isSuccessful) {
                val result = response.body()
                result?.topCategoryList?.forEach {
                    if (it.idTopCategory == TOP_CATEGORY_ALL_ID) {
                        return@withContext DataResult.Success(it)
                    }
                }
                DataResult.Success(null)
            } else {
                DataResult.Error(Exception(response.message()))
            }
        }


    override suspend fun checkBookInMyBooks(bookId: String): DataResult<Boolean> =
        try {
            val myBookItem = bookDao.findMyBookByBookId(bookId)
            DataResult.Success(myBookItem != null)
        } catch (e: Exception) {
            DataResult.Error(e)
        }

    override suspend fun addToMyBooks(bookId: String): DataResult<Any> =
        try {
            bookDao.addToMyBooks(
                MyBookItem(
                    bookId = bookId,
                    time = System.currentTimeMillis()
                )
            )
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error(e)
        }

    override suspend fun removeFromMyBooks(bookId: String): DataResult<Any> =
        try {
            bookDao.removeFromMyBooks(bookId)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error(e)
        }
}