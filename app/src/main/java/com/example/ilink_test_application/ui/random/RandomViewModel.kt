package com.example.ilink_test_application.ui.random

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ilink_test_application.data.entities.Animal
import com.example.ilink_test_application.data.repository.AnimalRepository
import com.example.ilink_test_application.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Class of viewmodel
 *
 * @property[animalRepository] repository for manage local and remote data sources
 * @property[glideRequestManager] glide request manager
 *
 * @property[lastAnimal] last get [Animal] from [AnimalRepository]
 * @property[isFavorite] value is animal in local database
 *
 * @property[getRandomCat] provides [Animal] from CatAPI
 * @property[getRandomDuck] provides [Animal] from DuckAPI
 * @property[getAllAnimal] provide animals from local database
 */
@HiltViewModel
class RandomViewModel @Inject constructor(
    private val animalRepository: AnimalRepository,
    private val glideRequestManager: RequestManager,
) : ViewModel() {

    var lastAnimal: Animal? = null

    val getRandomCat: LiveData<Resource<Animal>>
        get() = animalRepository.getRandomCat()
    val getRandomDuck: LiveData<Resource<Animal>>
        get() = animalRepository.getRandomDuck()
    val getAllAnimal: LiveData<List<Animal>>
        get() = animalRepository.getAllAnimals()

    /**
     * Function to get if animal in local database
     */
    suspend fun isFavorite(): Boolean = viewModelScope.async {
        animalRepository.isFavorite(lastAnimal!!)
    }.await()

    /**
     * Function to save animal into local database
     */
    fun saveFavoriteAnimal() = viewModelScope.launch {
        if (lastAnimal != null) {
            animalRepository.saveAnimal(lastAnimal!!)
        }
    }

    /**
     * Function to get [Drawable] from [url]
     */
    suspend fun getDrawable(url: String): Drawable = withContext(Dispatchers.IO) {
        glideRequestManager
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .centerCrop()
            .submit()
            .get()
    }

    /**
     * Function to delete animal from local database
     */
    fun deleteFavoriteAnimal() = viewModelScope.launch {
        if (lastAnimal != null) {
            animalRepository.deleteAnimal(lastAnimal!!)
        }
    }
}
