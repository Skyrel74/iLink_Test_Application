package com.example.ilink_test_application.data.remote

import com.example.ilink_test_application.data.entities.Animal
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface to make network request and get response for CatAPI
 *
 * @author ilya.rakipov@gmail.com
 */
interface CatService {

    /**
     * Function to make request and get random cat in response
     */
    @GET("rest")
    suspend fun getRandomCat(): Response<Animal>
}
