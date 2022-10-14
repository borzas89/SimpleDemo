package com.example.simepledemo.util

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Util {

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

    inline fun Fragment.launchAndRepeatWithViewLifecycle(
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        crossinline block: suspend CoroutineScope.() -> Unit,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
                block()
            }
        }
    }
}