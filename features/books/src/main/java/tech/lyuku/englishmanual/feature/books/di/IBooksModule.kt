package tech.lyuku.englishmanual.feature.books.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.lyuku.englishmanual.feature.books.data.db.BookDao
import tech.lyuku.englishmanual.feature.books.data.db.IBookDao
import tech.lyuku.englishmanual.feature.books.data.repository.BooksRepositoryImpl
import tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository

@InstallIn(SingletonComponent::class)
@Module
interface IBooksModule {

    @Binds
    fun bindBooksRepository(booksRepositoryImpl: BooksRepositoryImpl): IBooksRepository

    @Binds
    fun bindBookDao(bookDao: BookDao): IBookDao
}
