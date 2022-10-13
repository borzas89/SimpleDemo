package com.example.simepledemo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simepledemo.model.PhotoRemoteKeys

@Dao
interface PhotoRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<PhotoRemoteKeys>)

    @Query("SELECT * FROM photo_remote_keys WHERE photoId = :photoId")
    fun remoteKeysById(photoId: String): PhotoRemoteKeys?

    @Query("DELETE FROM photo_remote_keys")
    fun clearRemoteKeys()
}