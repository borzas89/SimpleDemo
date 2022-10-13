package com.example.simepledemo.navigator

import android.app.Activity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.example.simepledemo.R
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    private val activity: Activity
) : AppNavigator {

    private val navController by lazy {
        Navigation.findNavController(activity, R.id.nav_host_fragment)
    }

    override fun navigateToDetail(id: String, url: String) {
        val bundle = bundleOf("photoId" to id, "photoUrl" to url)
        navController.navigate(R.id.DetailFragment, args = bundle)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}