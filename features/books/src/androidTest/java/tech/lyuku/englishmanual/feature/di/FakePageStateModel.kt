package tech.lyuku.englishmanual.feature.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import tech.lyuku.englishmanual.base.core.base.PageStateObserver
import tech.lyuku.englishmanual.feature.fake.PageStateObserverImpl
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [tech.lyuku.englishmanual.base.di.PageStateModel::class]
)
class FakePageStateModel {

    @Singleton
    @Provides
    fun provideEmptyPageStateObserver(): PageStateObserver {
        return PageStateObserverImpl()
    }
}