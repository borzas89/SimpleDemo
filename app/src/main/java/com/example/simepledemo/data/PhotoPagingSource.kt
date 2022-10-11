package com.example.simepledemo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simepledemo.api.FlickrService
import com.example.simepledemo.model.Photo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_KEY = 0

class PhotoPagingSource @Inject constructor(
    private val service: FlickrService,
    private val query: String
): PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: STARTING_KEY
        return try {
            val response = service.fetchImagesByQuery(query = query,position)
            val photos = response.photos.photo.map {
                    photo ->
                Photo(
                    id = photo.id,
                    url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
                    title = photo.title
                )
            }
            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_KEY) null else position,
                nextKey = if (photos.isEmpty()) null else position + 1

            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}