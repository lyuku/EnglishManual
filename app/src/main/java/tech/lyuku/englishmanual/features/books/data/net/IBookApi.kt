package tech.lyuku.englishmanual.features.books.data.net

import retrofit2.Response
import retrofit2.http.GET
import tech.lyuku.englishmanual.models.BookCategoryList

interface IBookApi {

    @GET("/mock/book/all")
    suspend fun getBooksGroupedByCategory(): Response<BookCategoryList>
}