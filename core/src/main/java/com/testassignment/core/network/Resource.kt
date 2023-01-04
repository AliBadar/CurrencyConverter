package com.testassignment.core.network

// wrap data with status and message
data class Resource<T>(var status: Status, var data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(
                status = Status.SUCCESS,
                data = data,
                message = null
            )

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(
                status = Status.ERROR,
                data = data,
                message = message
            )

        fun <T> loading(data: T?): Resource<T> =
            Resource(
                status = Status.LOADING,
                data = data,
                message = null
            )
    }
}