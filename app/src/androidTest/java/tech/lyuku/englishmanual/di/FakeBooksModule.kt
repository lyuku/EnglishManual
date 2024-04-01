package tech.lyuku.englishmanual.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import tech.lyuku.englishmanual.fake.FakeBooksRepository
import tech.lyuku.englishmanual.features.books.data.db.BookDao
import tech.lyuku.englishmanual.features.books.data.db.IBookDao
import tech.lyuku.englishmanual.features.books.data.repository.IBooksRepository
import tech.lyuku.englishmanual.features.books.di.IBooksModule

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [IBooksModule::class]
)
interface FakeBooksModule {

    @Binds
    fun bindBooksRepository(fakeBooksRepository: FakeBooksRepository): IBooksRepository

    @Binds
    fun bindBookDao(bookDao: BookDao): IBookDao
}
