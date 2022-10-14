package com.example.simepledemo.ui.list

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.simepledemo.base.BaseViewModel
import com.example.simepledemo.base.Event
import com.example.simepledemo.data.DatastoreRepository
import com.example.simepledemo.data.PhotoRepository
import com.example.simepledemo.model.Photo
import com.example.simepledemo.util.Util.toFlowable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val DEFAULT_QUERY = "dog"
private const val DEBOUNCE_RATE_IN_MILLIS = 1500L

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: PhotoRepository,
    private val datastoreRepository: DatastoreRepository
) : BaseViewModel(), PhotoItemListener {
    val showDetail = MutableLiveData<Event<Photo>>()
    val searchText = ObservableField(getLastQuery() ?: DEFAULT_QUERY)

    fun getPhotos(): Flowable<PagingData<Photo>> =
        searchText
            .toFlowable()
            .distinctUntilChanged()
            .observeOn(Schedulers.io())
            .debounce(DEBOUNCE_RATE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .flatMap { query ->
                saveLastQuery(query)
                repository
                    .getPhotosByName(query = query)
            }.cachedIn(viewModelScope)


    override fun onPhotoClicked(photo: Photo) {
        showDetail.value = Event(photo)
    }

    private fun saveLastQuery(value: String) {
        viewModelScope.launch {
            datastoreRepository.putString(LAST_SEARCH_QUERY, value)
        }
    }

    private fun getLastQuery(): String? = runBlocking {
        datastoreRepository.getString(LAST_SEARCH_QUERY)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
