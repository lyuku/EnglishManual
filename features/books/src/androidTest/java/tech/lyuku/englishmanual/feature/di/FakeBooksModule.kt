package tech.lyuku.englishmanual.feature.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import tech.lyuku.englishmanual.feature.fake.FakeBooksRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [tech.lyuku.englishmanual.feature.books.di.IBooksModule::class]
)
interface FakeBooksModule {

    @Binds
    fun bindBooksRepository(fakeBooksRepository: FakeBooksRepository): tech.lyuku.englishmanual.feature.books.data.repository.IBooksRepository

    @Binds
    fun bindBookDao(bookDao: tech.lyuku.englishmanual.feature.books.data.db.BookDao): tech.lyuku.englishmanual.feature.books.data.db.IBookDao
}
