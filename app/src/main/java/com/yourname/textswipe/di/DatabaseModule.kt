package com.yourname.textswipe.di

import android.content.Context
import androidx.room.Room
import com.yourname.textswipe.data.local.AppDatabase
import com.yourname.textswipe.data.local.dao.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database.db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(
        database: AppDatabase
    ): BookmarkDao {
        return database.bookmarkDao()
    }
}