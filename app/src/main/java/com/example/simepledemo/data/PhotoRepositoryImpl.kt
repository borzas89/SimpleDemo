package com.example.simepledemo.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.simepledemo.api.FlickrService
import com.example.simepledemo.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val service: FlickrService
): PhotoRepository {

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    override fun getPhotosByName(query: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotoPagingSource(service,query) }
        ).flow
    }

}