package com.example.simepledemo.ui.detail

import com.example.simepledemo.base.BaseViewModel
import com.example.simepledemo.data.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    repository: PhotoRepository
): BaseViewModel(){

    init {
        // TODO change with real id
        repository.getPhotoById("122312")
            .map {
                it.title
            }.subscribe()
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}