package com.lydiatest.contactapp.model

data class LoadingState(var state: Status, var msg: String? = null, var resId: Int? = null) {

    enum class Status {
        RUNNING, FAILED, SUCCESS
    }
}