package com.lydiatest.contactapp.utils

import io.reactivex.Observable
import io.reactivex.Scheduler

/* Created by *-----* Alexandre Thauvin *-----* */

class SchedulerProvider(private val backgroundScheduler: Scheduler, private val foregroundScheduler: Scheduler) {

    fun <T> getSchedulersForObservable(): (Observable<T>) -> Observable<T> {
        return { observable: Observable<T> ->
            observable.subscribeOn(backgroundScheduler)
                .observeOn(foregroundScheduler)
        }
    }
}