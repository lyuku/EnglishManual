package tech.lyuku.englishmanual.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import tech.lyuku.englishmanual.core.db.IRealmProvider
import tech.lyuku.englishmanual.core.db.RealmProvider
import tech.lyuku.englishmanual.core.network.RetrofitInstance
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModel {

    @Singleton
    @Provides
    fun retrofit(): Retrofit = RetrofitInstance.retrofit

    @Singleton
    @Provides
    fun provideRealmProvider(
        @ApplicationContext appContext: Context,
        @RealmDispatcher realmDispatcher: CoroutineDispatcher
    ): IRealmProvider =
        RealmProvider(appContext, realmDispatcher)

}