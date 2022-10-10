package com.example.simepledemo.di

import com.example.simepledemo.navigator.AppNavigator
import com.example.simepledemo.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    @Binds
    abstract fun bindNavigator(navigator: AppNavigatorImpl): AppNavigator
}