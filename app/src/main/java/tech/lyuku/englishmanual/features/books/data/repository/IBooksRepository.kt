package tech.lyuku.englishmanual.features.books.data.repository

import tech.lyuku.englishmanual.core.base.DataResult
import tech.lyuku.englishmanual.models.TopCategory

interface IBooksRepository {

    /**
     * Get all books category
     */
    suspend fun getBooksGroupedByCategory(): DataResult<TopCategory>
}
