package com.freight.shipper.extensions

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData


/**
 * @CreatedBy Sanjay Kushwah on 8/10/2019.
 * sanjaykushwah0601@gmail.com
 */
open class BaseRepository {
    fun <R, E> setupObserver(observer: Pair<MediatorLiveData<R>, MediatorLiveData<E>>)
            : Pair<MutableLiveData<R>, MutableLiveData<E>> {
        val pair = Pair(MutableLiveData<R>(), MutableLiveData<E>())
        observer.first.addSource(pair.first) { observer.first.postValue(it) }
        observer.second.addSource(pair.second) { observer.second.postValue(it) }
        return pair
    }
}