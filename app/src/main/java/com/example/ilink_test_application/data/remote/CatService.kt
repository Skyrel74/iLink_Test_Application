package com.example.ilink_test_application.data.remote

import com.example.ilink_test_application.data.entities.Animal
import retrofit2.Response
import retrofit2.http.GET

interface CatService {

    @GET("rest")
    suspend fun getRandomCat(): Response<Animal>
}
