package com.example.ilink_test_application.data.remote

import com.example.ilink_test_application.data.entities.Animal
import com.example.ilink_test_application.data.repository.AnimalRepository
import javax.inject.Inject

/**
 * Class to managing remote data source [Animal] for [AnimalRepository]
 *
 * @param[duckService] is using to get remote data from DuckAPI
 *
 * @author ilya.rakipov@gmail.com
 */
class DuckRemoteDataSource @Inject constructor(
    private val duckService: DuckService
) : BaseRemoteDataSource() {

    /**
     * Function to get random cat from remote data DuckAPI
     */
    suspend fun getRandomDuck() = getResult { duckService.getRandomDuck() }
}
