package com.kiri.common.utils

class Resource<out T>(val status: Status, val data: ApiResponse<T>?, val error: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: ApiResponse<T>?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: String, data: ApiResponse<T>? = null): Resource<T> {
            return Resource(Status.ERROR, data, error)
        }

        fun <T> loading(data: ApiResponse<T>? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
