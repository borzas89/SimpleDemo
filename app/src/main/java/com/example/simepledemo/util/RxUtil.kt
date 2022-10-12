package com.example.simepledemo.util

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

object RxUtil {

    fun <T> ObservableField<T>.toFlowable(): Flowable<T> {
        return Flowable.create({ emitter ->
            get()?.let { nextValue -> emitter.onNext(nextValue) }

            val callback = object : Observable.OnPropertyChangedCallback() {

                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    get()?.let { nextValue -> emitter.onNext(nextValue) }
                }
            }

            addOnPropertyChangedCallback(callback)
            emitter.setCancellable { removeOnPropertyChangedCallback(callback) }
        }, BackpressureStrategy.BUFFER)
    }
}