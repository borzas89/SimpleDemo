package com.example.simepledemo.ui.detail

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.simepledemo.base.BaseViewModel
import com.example.simepledemo.data.PhotoRepository
import com.example.simepledemo.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PhotoRepository
) : BaseViewModel() {
    private val _photo = MutableLiveData<Photo>()
    val photo: LiveData<Photo> = _photo
    val imageUrl = ObservableField<String>()

    init {
        if (savedStateHandle.contains("photoId")) {
            val retrievedId = savedStateHandle.getLiveData("photoId", "abc123").value

            val retrievedUrl = savedStateHandle.getLiveData("photoUrl", "abc123").value
            retrievedUrl.let {
                imageUrl.set(it)
            }
            retrievedId?.let {
                repository.getPhotoById(retrievedId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy {
                        _photo.postValue(it)
                    }.addTo(compositeDisposable)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}