package com.example.ilink_test_application.utils

import retrofit2.Response

/**
 * Class to contain [data] of remote data sources
 *
 * @param[status] status of [Response]
 * @param[data] container for any data
 * @param[message] error message
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    /**
     * Enum class to contain readable statuses
     */
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        /**
         * Function to inform [Resource] is [Status.SUCCESS]
         *
         * @param[data] data to contain
         *
         * @return[Resource]
         */
        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS, data, null)

        /**
         * Function to inform [Resource] is [Status.ERROR]
         *
         * @param[message] message of error
         * @param[data] data to contain if exists
         *
         * @return[Resource]
         */
        fun <T> error(message: String, data: T? = null): Resource<T> =
            Resource(Status.ERROR, data, message)

        /**
         * Function to inform [Resource] is [Status.LOADING]
         *
         * @param[data] data to contain if exists
         *
         * @return[Resource]
         */
        fun <T> loading(data: T? = null): Resource<T> = Resource(Status.LOADING, data, null)
    }
}
