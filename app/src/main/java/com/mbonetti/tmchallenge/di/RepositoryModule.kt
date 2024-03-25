package com.mbonetti.tmchallenge.di

import com.mbonetti.tmchallenge.repository.EventRepository
import com.mbonetti.tmchallenge.repository.EventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun  bindEventRepository(
        eventRepositoryImpl: EventRepositoryImpl
    ): EventRepository
}
