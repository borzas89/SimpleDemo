package com.example.simepledemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simepledemo.model.Photo
import com.example.simepledemo.model.PhotoRemoteKeys

@Database(
    entities = [
        Photo::class,
        PhotoRemoteKeys::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun photoRemoteKeysDao(): PhotoRemoteKeysDao
}