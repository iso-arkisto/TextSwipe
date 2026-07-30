package com.yourname.textswipe.di

import com.yourname.textswipe.data.repository.FeedRepositoryImpl
import com.yourname.textswipe.domain.repository.FeedRepository
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
    abstract fun bindFeedRepository(
        feedRepositoryImpl: FeedRepositoryImpl
    ): FeedRepository
}