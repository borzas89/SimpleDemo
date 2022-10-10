package com.example.simepledemo.navigator

import android.app.Activity
import androidx.navigation.Navigation
import com.example.simepledemo.R
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    private val activity: Activity
) : AppNavigator {

    private val navController by lazy {
        Navigation.findNavController(activity, R.id.nav_host_fragment)
    }

    override fun navigateToDetail() {
        navController.navigate(R.id.DetailFragment)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}