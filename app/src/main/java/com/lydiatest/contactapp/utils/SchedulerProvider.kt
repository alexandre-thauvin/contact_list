package com.lydiatest.contactapp.utils

import io.reactivex.*

/* Created by *-----* Alexandre Thauvin *-----* */

class SchedulerProvider(val backgroundScheduler: Scheduler, val foregroundScheduler: Scheduler) {

    fun <T> getSchedulersForObservable(): (Observable<T>) -> Observable<T> {
        return { observable: Observable<T> ->
            observable.subscribeOn(backgroundScheduler)
                .observeOn(foregroundScheduler)
        }
    }
}