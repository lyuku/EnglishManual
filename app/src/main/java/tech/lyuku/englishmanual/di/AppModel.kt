package tech.lyuku.englishmanual.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import retrofit2.Retrofit
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
    fun provideRealmInstance(): Realm = Realm.getDefaultInstance()

}