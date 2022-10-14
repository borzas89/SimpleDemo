package com.example.simepledemo.data

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.simepledemo.api.FlickrService
import com.example.simepledemo.database.AppDatabase
import com.example.simepledemo.model.Photo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val STARTING_KEY = 0

class PhotoPagingSource @Inject constructor(
    private val service: FlickrService,
    private val query: String,
    private val database: AppDatabase,
    private val mapper: PhotoDtoToModelMapper
): RxPagingSource<Int, Photo>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Photo>> {
        val position = params.key ?: STARTING_KEY

        return service.fetchImagesByQuery(query, position)
            .subscribeOn(Schedulers.io())
            .map {
                mapper.map(it.photos.photo)
            }
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: List<Photo>, position: Int): LoadResult<Int, Photo> {
        // TODO delete from here... just to test db saving
        database.photoDao().save(photos = data).subscribe()
        return LoadResult.Page(
            data = data,
            prevKey = if (position == STARTING_KEY) null else position,
            nextKey = if (data.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}