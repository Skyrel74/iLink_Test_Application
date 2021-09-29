package com.example.ilink_test_application.data.remote

import com.example.ilink_test_application.data.entities.Animal
import com.example.ilink_test_application.data.repository.AnimalRepository
import javax.inject.Inject

/**
 * Class to managing remote data source [Animal] for [AnimalRepository]
 *
 * @param[catService] is using to get remote data from CatAPI
 *
 * @author ilya.rakipov@gmail.com
 */
class CatRemoteDataSource @Inject constructor(
    private val catService: CatService,
) : BaseRemoteDataSource() {

    /**
     * Function to get random cat from remote data CatAPI
     */
    suspend fun getRandomCat() = getResult { catService.getRandomCat() }
}
