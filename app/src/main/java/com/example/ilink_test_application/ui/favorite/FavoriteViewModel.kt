package com.example.ilink_test_application.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ilink_test_application.data.entities.Animal
import com.example.ilink_test_application.data.repository.AnimalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val animalRepository: AnimalRepository,
) : ViewModel() {

    val favoriteAnimals = animalRepository.getAllAnimals()

    fun deleteAnimal(animal: Animal) =
        viewModelScope.launch { animalRepository.deleteAnimal(animal) }
}