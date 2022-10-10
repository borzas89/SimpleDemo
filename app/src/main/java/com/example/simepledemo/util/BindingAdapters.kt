package com.example.simepledemo.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simepledemo.R
import com.example.simepledemo.base.BaseAdapter
import com.example.simepledemo.base.BaseModel

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
    @BindingAdapter("setAdapter")
    fun setAdapter(
        recyclerView: RecyclerView,
        adapter: BaseAdapter<ViewDataBinding, BaseModel>?
    ) {
        adapter?.let {
            recyclerView.adapter = it
        }
    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    @BindingAdapter("submitList")
    fun submitList(recyclerView: RecyclerView, list: List<BaseModel>?) {
        val adapter = recyclerView.adapter as BaseAdapter<ViewDataBinding, BaseModel>?
        adapter?.updateData(list ?: listOf())
    }


}
