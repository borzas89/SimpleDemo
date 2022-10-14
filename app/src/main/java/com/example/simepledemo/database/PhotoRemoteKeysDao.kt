package com.example.simepledemo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simepledemo.model.PhotoRemoteKeys
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface PhotoRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<PhotoRemoteKeys>) : Completable

    @Query("SELECT * FROM photo_remote_keys WHERE photoId = :photoId")
    fun remoteKeysById(photoId: String): Maybe<PhotoRemoteKeys>

    @Query("DELETE FROM photo_remote_keys")
    fun clearRemoteKeys(): Completable
}