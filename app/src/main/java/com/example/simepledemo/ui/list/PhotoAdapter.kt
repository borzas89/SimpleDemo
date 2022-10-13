package com.example.simepledemo.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.simepledemo.databinding.PhotoViewholderBinding
import com.example.simepledemo.model.Photo

class PhotoAdapter(private val listener: PhotoItemListener
) : PagingDataAdapter<Photo, PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            PhotoViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile, clickListener = listener)
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem
        }
    }
}

interface PhotoItemListener {
    fun onPhotoClicked(photo: Photo)
}
