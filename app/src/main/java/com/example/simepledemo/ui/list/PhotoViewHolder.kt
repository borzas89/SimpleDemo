package com.example.simepledemo.ui.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simepledemo.R
import com.example.simepledemo.databinding.PhotoViewholderBinding
import com.example.simepledemo.model.Photo

class PhotoViewHolder(
    private val binding: PhotoViewholderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photo: Photo, clickListener: PhotoItemListener) {
        binding.imageView.setOnClickListener {
           clickListener.onPhotoClicked(photo)
        }
        binding.apply {
            Glide.with(imageView.context)
                .load(photo.url)
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .into(imageView)
        }
    }
}