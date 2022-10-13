package com.example.simepledemo.data

import androidx.paging.PagingData
import com.example.simepledemo.model.Photo
import io.reactivex.Flowable
import io.reactivex.Maybe

interface PhotoRepository{
    fun getPhotosByName(query: String): Flowable<PagingData<Photo>>
    fun getPhotoById(id: String): Maybe<Photo>
}