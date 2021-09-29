package com.example.ilink_test_application.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.ilink_test_application.data.entities.Animal
import com.example.ilink_test_application.data.local.AnimalDao
import com.example.ilink_test_application.data.remote.CatRemoteDataSource
import com.example.ilink_test_application.data.remote.DuckRemoteDataSource
import com.example.ilink_test_application.utils.Resource
import kotlinx.coroutines.*
import java.io.File
import java.net.URI
import javax.inject.Inject

/**
 * Class to get animal data from local and remote data sources
 *
 * @param[remoteCatDataSource] provide [Animal] from remote CatAPI
 * @param[remoteDuckDataSource] provide [Animal] from remote DuckAPI
 * @param[localAnimalDataSource] provide object to manage data in local database
 *
 * @author ilya.rakipov@gmail.com
 */
class AnimalRepository @Inject constructor(
    private val remoteCatDataSource: CatRemoteDataSource,
    private val remoteDuckDataSource: DuckRemoteDataSource,
    private val localAnimalDataSource: AnimalDao,
) {

    /**
     * Function to get random [Animal] from remote CatAPI
     */
    fun getRandomCat() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val response = remoteCatDataSource.getRandomCat()
        if (response.status == Resource.Status.SUCCESS) {
            emit(Resource.success(response.data!!))
        } else if (response.status == Resource.Status.ERROR) {
            emit(Resource.error(response.message!!))
        }
    }

    /**
     * Function to get random [Animal] from remote DuckAPI
     */
    fun getRandomDuck() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val response = remoteDuckDataSource.getRandomDuck()
        if (response.status == Resource.Status.SUCCESS) {
            emit(Resource.success(response.data!!))
        } else if (response.status == Resource.Status.ERROR) {
            emit(Resource.error(response.message!!))
        }
    }

    /**
     * Function to save [animal] in local database
     */
    suspend fun saveAnimal(animal: Animal) = withContext(Dispatchers.IO) {
        launch {
            localAnimalDataSource.insertAnimal(animal)
        }
    }

    /**
     * Function to delete [animal] from local database and local storage
     */
    suspend fun deleteAnimal(animal: Animal) = withContext(Dispatchers.IO) {
        launch {
            val uri = URI(animal.fileUri)
            val file = File(uri)
            file.delete()
            localAnimalDataSource.deleteAnimal(animal)
        }
    }

    /**
     * Function to get saved animals in database
     */
    fun getAllAnimals(): LiveData<List<Animal>> = liveData(Dispatchers.IO) {
        emitSource(localAnimalDataSource.getAllAnimals())
    }

    /**
     * Function to get to know if [animal] in table
     */
    suspend fun isFavorite(animal: Animal): Boolean {
        val isFav: Deferred<Boolean> = withContext(Dispatchers.IO) {
            async {
                !localAnimalDataSource.isFavorite(animal.url).isNullOrEmpty()
            }
        }
        return isFav.await()
    }
}
