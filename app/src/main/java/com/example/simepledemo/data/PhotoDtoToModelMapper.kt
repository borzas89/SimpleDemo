package com.example.simepledemo.data

import com.example.simepledemo.api.PhotoResponse
import com.example.simepledemo.base.ApiModelToModelMapper
import com.example.simepledemo.model.Photo
import javax.inject.Inject

class PhotoDtoToModelMapper @Inject constructor() :
    ApiModelToModelMapper<PhotoResponse, Photo>() {

    override fun map(model: PhotoResponse) =
        Photo(
            id = model.id,
            url = "https://farm${model.farm}.staticflickr.com/${model.server}/${model.id}_${model.secret}.jpg",
            title = model.title
        )
}