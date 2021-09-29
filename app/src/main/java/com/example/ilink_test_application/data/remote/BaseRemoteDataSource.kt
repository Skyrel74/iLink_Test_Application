package com.example.ilink_test_application.data.remote

import android.util.Log
import com.example.ilink_test_application.utils.Resource
import retrofit2.Response

/**
 * Class to generalize remote data source functional
 *
 * @author ilya.rakipov@gmail.com
 */
abstract class BaseRemoteDataSource {

    /**
     * Function to try get and return network [call] result or
     * return [error] if network [call] body is empty,
     * response is not successful or
     * network [call] causes [Exception]
     *
     * @return[Resource]
     */
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    /**
     * Function to handle [Exception], log [message] and
     * set info into [Resource] to show it to user
     *
     * @return[Resource.error]
     */
    private fun <T> error(message: String): Resource<T> {
        Log.e("Data sourse", message)
        return Resource.error("Нет интернет подключения")
    }
}
