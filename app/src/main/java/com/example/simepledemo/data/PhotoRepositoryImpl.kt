package com.example.simepledemo.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.simepledemo.api.FlickrService
import com.example.simepledemo.database.AppDatabase
import com.example.simepledemo.database.PhotoDao
import com.example.simepledemo.model.Photo
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val service: FlickrService,
    private val photoDao: PhotoDao
): PhotoRepository {

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    override fun getPhotosByName(query: String): Flowable<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 5),
            pagingSourceFactory = { PhotoPagingSource(service, query) }
        ).flowable
    }

    override fun getPhotoById(id: String): Maybe<Photo> =
        photoDao.getById(id)
            .subscribeOn(Schedulers.io())
}