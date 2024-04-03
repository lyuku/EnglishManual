package tech.lyuku.englishmanual.feature.books.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import tech.lyuku.englishmanual.feature.books.data.api.IBookApi
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