package com.example.ilink_test_application.ui.random

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ilink_test_application.data.entities.Animal
import com.example.ilink_test_application.data.repository.AnimalRepository
import com.example.ilink_test_application.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val animalRepository: AnimalRepository,
    private val glideRequestManager: RequestManager,
) : ViewModel() {

    var lastAnimal: Animal? = null
    var isFavorite = false

    val getRandomCat: LiveData<Resource<Animal>>
        get() = animalRepository.getRandomCat()
    val getRandomDuck: LiveData<Resource<Animal>>
        get() = animalRepository.getRandomDuck()
    val getAllAnimal: LiveData<List<Animal>>
        get() = animalRepository.getAllAnimals()
    fun isFav() = animalRepository.getAllAnimals().map { animalList ->
        animalList.any { animal ->
            animal.url == lastAnimal?.url
        }
    }

    fun saveFavoriteAnimal() = viewModelScope.launch {
        if (lastAnimal != null) {
            animalRepository.saveAnimal(lastAnimal!!)
        }
    }

    suspend fun getDrawable(url: String): Drawable = withContext(Dispatchers.IO) {
        glideRequestManager
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .centerCrop()
            .submit()
            .get()
    }

    fun deleteFavoriteAnimal() = viewModelScope.launch {
        if (lastAnimal != null) {
            animalRepository.deleteAnimal(lastAnimal!!)
        }
    }
}
