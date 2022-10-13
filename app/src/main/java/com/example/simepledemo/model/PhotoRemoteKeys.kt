package com.example.simepledemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_remote_keys")
data class PhotoRemoteKeys(
    @PrimaryKey val photoId: String,
    val prevKey: Int?,
    val nextKey: Int?
)