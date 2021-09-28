package com.example.ilink_test_application.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.ilink_test_application.data.entities.Animal
import com.example.ilink_test_application.data.local.AnimalDao
import com.example.ilink_test_application.data.remote.CatRemoteDataSource
import com.example.ilink_test_application.data.remote.DuckRemoteDataSource
import com.example.ilink_test_application.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URI
import javax.inject.Inject

class AnimalRepository @Inject constructor(
    private val remoteCatDataSource: CatRemoteDataSource,
    private val remoteDuckDataSource: DuckRemoteDataSource,
    private val localAnimalDataSource: AnimalDao,
) {

    fun getRandomCat() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val response = remoteCatDataSource.getRandomCat()
        if (response.status == Resource.Status.SUCCESS) {
            emit(Resource.success(response.data!!))
        } else if (response.status == Resource.Status.ERROR) {
            emit(Resource.error(response.message!!))
        }
    }

    fun getRandomDuck() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val response = remoteDuckDataSource.getRandomDuck()
        if (response.status == Resource.Status.SUCCESS) {
            emit(Resource.success(response.data!!))
        } else if (response.status == Resource.Status.ERROR) {
            emit(Resource.error(response.message!!))
        }
    }

    suspend fun saveAnimal(animal: Animal) = withContext(Dispatchers.IO) {
        launch {

            localAnimalDataSource.insertAnimal(animal)
        }
    }

    suspend fun deleteAnimal(animal: Animal) = withContext(Dispatchers.IO) {
        launch {
            val uri = URI(animal.fileUri)
            val file = File(uri)
            file.delete()
            localAnimalDataSource.deleteAnimal(animal)
        }
    }

    fun getAllAnimals(): LiveData<List<Animal>> = liveData(Dispatchers.IO) {
        emitSource(localAnimalDataSource.getAllAnimals())
    }

    fun isFavoriteAnimal(animal: Animal): Boolean = liveData(Dispatchers.IO) {
        emit(localAnimalDataSource.getAllAnimals().value?.contains(animal))
    }.value ?: false
}