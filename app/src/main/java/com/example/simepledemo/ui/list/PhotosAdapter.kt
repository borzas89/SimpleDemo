package com.example.simepledemo.ui.list

import com.example.simepledemo.R
import com.example.simepledemo.base.BaseAdapter
import com.example.simepledemo.databinding.ListItemBinding
import com.example.simepledemo.model.Photo

class PhotosAdapter (
    private val list: List<Photo>,
    private val photoListener: PhotoListener
) : BaseAdapter<ListItemBinding, Photo>(list) {

    override val layoutId: Int = R.layout.list_item

    override fun bind(binding: ListItemBinding, item: Photo) {
        binding.apply {
            photo = item
            listener = photoListener
            executePendingBindings()
        }
    }
}

interface PhotoListener {
    fun onPhotoClicked(photo: Photo)
}