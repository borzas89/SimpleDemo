package com.example.simepledemo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simepledemo.base.Event
import com.example.simepledemo.data.PhotoRepository
import com.example.simepledemo.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val DEFAULT_QUERY = "dog"

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: PhotoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), PhotoListener {
    private val mutablePhotosList = MutableLiveData<PagingData<Photo>>()
    val photosList: LiveData<PagingData<Photo>> = mutablePhotosList

    val showDetail = MutableLiveData<Event<Photo>>()
    val queryFlow = MutableStateFlow(DEFAULT_QUERY)
    val items: Flow<PagingData<Photo>>


    init {
        val initialQuery: String = savedStateHandle[LAST_SEARCH_QUERY] ?: DEFAULT_QUERY
        items = repository.getPhotosByName(initialQuery).cachedIn(viewModelScope)
    }

    override fun onPhotoClicked(photo: Photo) {
        showDetail.value = Event(photo)
    }

    fun setCurrentQuery(query: String) {
        queryFlow.value = query
    }

    override fun onCleared() {
        super.onCleared()
    }

}
