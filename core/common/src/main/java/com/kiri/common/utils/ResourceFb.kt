package com.kiri.common.utils

class ResourceFb<out T>(val status: Resource.Status, val data: T?, val error: String?) {
    companion object {
        fun <T> success(data: T): ResourceFb<T> {
            return ResourceFb(Resource.Status.SUCCESS, data, null)
        }

        fun <T> error(error: String, data: T? = null): ResourceFb<T> {
            return ResourceFb(Resource.Status.ERROR, data, error)
        }

        fun <T> loading(data: T? = null): ResourceFb<T> {
            return ResourceFb(Resource.Status.LOADING, data, null)
        }
    }
}
