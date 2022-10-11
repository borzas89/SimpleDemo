package com.example.simepledemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simepledemo.model.Photo

@Database(
    entities = [
        Photo::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}