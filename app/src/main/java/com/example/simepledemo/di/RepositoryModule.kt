package com.example.simepledemo.di

import com.example.simepledemo.data.PhotoRepository
import com.example.simepledemo.data.PhotoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindPhotoRepository(impl: PhotoRepositoryImpl): PhotoRepository
}