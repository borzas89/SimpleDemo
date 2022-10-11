package com.example.simepledemo.data

import androidx.paging.PagingData
import com.example.simepledemo.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository{
    fun getPhotosByName(query: String): Flow<PagingData<Photo>>
}