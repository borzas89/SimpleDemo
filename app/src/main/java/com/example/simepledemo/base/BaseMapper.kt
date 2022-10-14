package com.example.simepledemo.base

abstract class BaseMapper<T, R> {

    abstract fun map(model: T): R

    fun map(list: List<T>): List<R> =
        list.map(::map)
}