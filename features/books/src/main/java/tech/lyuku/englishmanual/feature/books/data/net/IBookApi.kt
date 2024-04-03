package tech.lyuku.englishmanual.feature.books.data.net

import retrofit2.Response
import retrofit2.http.GET
import tech.lyuku.englishmanual.data.models.BookCategoryList

interface IBookApi {

    @GET("/mock/book/all")
    suspend fun getBooksGroupedByCategory(): Response<BookCategoryList>
}