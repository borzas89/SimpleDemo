package com.example.simepledemo.navigator

interface AppNavigator {

    fun navigateToDetail(id: String, imageUrl: String)
    fun popBackStack()
}