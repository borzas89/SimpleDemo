package com.example.simepledemo.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.bumptech.glide.Glide
import com.example.simepledemo.R


object BindingAdapters {
    /**
     * Binding adapter that loads an image into an ImageView using Glide
     */
    @JvmStatic
    @BindingAdapter("image_url")
    fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .fitCenter()
            .into(imageView)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text")
    fun getValueFromBinding(view: TextView): String {
        return view.text.toString()
    }


}
