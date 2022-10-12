package com.example.simepledemo.ui.list

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.simepledemo.base.BaseViewModel
import com.example.simepledemo.base.Event
import com.example.simepledemo.data.PhotoRepository
import com.example.simepledemo.model.Photo
import com.example.simepledemo.util.RxUtil.toFlowable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val DEFAULT_QUERY = "dog"
private const val DEBOUNCE_RATE_IN_MILLIS = 1500L

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: PhotoRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(), PhotoListener {
    val showDetail = MutableLiveData<Event<Photo>>()

    val searchText = ObservableField(savedStateHandle[LAST_SEARCH_QUERY] ?: DEFAULT_QUERY)

    fun getPhotos(): Flowable<PagingData<Photo>> =
        searchText
            .toFlowable()
            .distinctUntilChanged()
            .debounce(DEBOUNCE_RATE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .flatMap {
                savedStateHandle[LAST_SEARCH_QUERY] = it
                repository
                    .getPhotosByName(query = it)
            }.cachedIn(viewModelScope)


    override fun onPhotoClicked(photo: Photo) {
        showDetail.value = Event(photo)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
