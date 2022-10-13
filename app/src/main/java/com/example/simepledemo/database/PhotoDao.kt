package com.example.simepledemo.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simepledemo.model.Photo

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(photos: List<Photo>) : Completable

    @Query("SELECT * FROM photos WHERE id = :id")
    fun getById(id: String): Maybe<Photo>

    @Query("SELECT * FROM photos")
    fun observeAll(): Observable<List<Photo>>

    @Query("DELETE FROM photos")
    fun deleteAll(): Completable

    @Query("SELECT * FROM photos WHERE title LIKE :queryString")
    fun getByName(queryString: String): PagingSource<Int, Photo>

    @Query("SELECT * FROM photos ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, Photo>
}