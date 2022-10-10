package com.example.simepledemo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simepledemo.api.FlickrService
import com.example.simepledemo.base.Event
import com.example.simepledemo.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val flickrService: FlickrService
): ViewModel(), PhotoListener {
    private val mutablePhotosListLiveData = MutableLiveData<List<Photo>>()
    val photosListLiveData: LiveData<List<Photo>> = mutablePhotosListLiveData
    val showDetail = MutableLiveData<Event<Photo>>()

    init {
       loadPhotos()
    }

    fun loadPhotos(): LiveData<List<Photo>> {
        viewModelScope.launch {
            val searchResponse = flickrService.fetchImages()
            val photosList = searchResponse.photos.photo.map { photo ->
                Photo(
                    id = photo.id,
                    url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
                    title = photo.title
                )
            }
            mutablePhotosListLiveData.postValue(photosList)
        }
        return photosListLiveData
    }

    override fun onPhotoClicked(photo: Photo) {
        showDetail.value = Event(photo)
    }
}