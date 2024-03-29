package tech.lyuku.englishmanual.features.books.data.repository

import tech.lyuku.englishmanual.core.base.DataResult
import tech.lyuku.englishmanual.features.books.data.net.IBookApi
import tech.lyuku.englishmanual.models.TopCategory
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val bookApi: IBookApi
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
}