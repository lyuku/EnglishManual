package tech.lyuku.englishmanual.features.books.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import tech.lyuku.englishmanual.features.books.data.net.IBookApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): IBookApi {
        return retrofit.create(IBookApi::class.java)
    }

}