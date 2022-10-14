package com.example.simepledemo.di

import android.content.Context
import com.example.simepledemo.data.DatastoreRepository
import com.example.simepledemo.data.DatastoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app: Context
    ): DatastoreRepository = DatastoreRepositoryImpl(app)

}