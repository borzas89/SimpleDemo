package com.example.simepledemo.api

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.flickr.com/services/rest/"
private const val API_KEY: String = "65803e8f6e4a3982200621cad356be51"
private const val PER_PAGE: Int = 20

/*
    API Sample calls
   https://api.flickr.com/services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1&text=dog&per_page=20&api_key=65803e8f6e4a3982200621cad356be51

   https://farm66.staticflickr.com/65535/52417152272_cdfef915b3.jpg
*/
interface FlickrService {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&text=dog&per_page=${PER_PAGE}&api_key=${API_KEY}")
    suspend fun fetchImages(): PhotoSearchResponse

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=${API_KEY}")
    fun fetchImagesByQuery(
        @Query("text") query: String,
        @Query("per_page") itemsPerPage: Int
    ): Single<PhotoSearchResponse>

    companion object {
        fun create(): FlickrService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(FlickrService::class.java)
        }
    }
}