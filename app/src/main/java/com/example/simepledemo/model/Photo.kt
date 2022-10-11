package com.example.simepledemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simepledemo.base.BaseModel

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey
    override val id: String,
    val url: String,
    val title: String
): BaseModel