package tech.lyuku.englishmanual.features.books.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.lyuku.englishmanual.features.books.data.repository.BooksRepositoryImpl
import tech.lyuku.englishmanual.features.books.data.repository.IBooksRepository

@InstallIn(SingletonComponent::class)
@Module
interface IBooksModule {

    @Binds
    fun bindBooksRepository(booksRepositoryImpl: BooksRepositoryImpl): IBooksRepository

}
