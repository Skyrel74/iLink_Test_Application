package com.example.ilink_test_application.data.remote

import com.example.ilink_test_application.data.entities.Animal
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface to make network request and get response for DuckAPI
 *
 * @author ilya.rakipov@gmail.com
 */
interface DuckService {

    /**
     * Function to make request and get random duck in response
     */
    @GET("random")
    suspend fun getRandomDuck(): Response<Animal>
}
