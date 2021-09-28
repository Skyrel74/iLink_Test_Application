package com.example.ilink_test_application.data.remote

import android.util.Log
import com.example.ilink_test_application.utils.Resource
import retrofit2.Response

abstract class BaseRemoteDataSource {

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

    private fun <T> error(message: String): Resource<T> {
        Log.e("Data sourse", message)
        return Resource.error("Нет интернет подключения")
    }
}
