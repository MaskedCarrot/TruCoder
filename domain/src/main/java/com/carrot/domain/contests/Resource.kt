package com.carrot.domain.contests

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class InternalServerError<T>(data: T?, message: String) : Resource<T>(data, message)
    class UncaughtError<T>(data: T? = null, message: String) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}
