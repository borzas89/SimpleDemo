package com.example.simepledemo.di

import android.content.Context
import androidx.room.Room
import com.example.simepledemo.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "simple.db"
        ).build()

    @Provides
    @Singleton
    fun providePhotosDao(database: AppDatabase) = database.photoDao()

    @Provides
    @Singleton
    fun providePhotoRemoteKeysDao(database: AppDatabase) = database.photoRemoteKeysDao()
}