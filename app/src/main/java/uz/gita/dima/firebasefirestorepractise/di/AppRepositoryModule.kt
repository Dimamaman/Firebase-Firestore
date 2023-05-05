package uz.gita.dima.firebasefirestorepractise.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dima.firebasefirestorepractise.domain.AppRepository
import uz.gita.dima.firebasefirestorepractise.domain.impl.AppRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)
interface AppRepositoryModule {

    @Binds
    fun getAppRepository(impl: AppRepositoryImpl): AppRepository
}