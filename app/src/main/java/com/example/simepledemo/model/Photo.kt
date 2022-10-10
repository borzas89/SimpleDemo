package com.example.simepledemo.model

import com.example.simepledemo.base.BaseModel

data class Photo(
    override val id: String,
    val url: String,
    val title: String
): BaseModel