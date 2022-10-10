package com.example.simepledemo.di

import com.example.simepledemo.api.FlickrService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideFlickrService(): FlickrService =
        FlickrService.create()
}