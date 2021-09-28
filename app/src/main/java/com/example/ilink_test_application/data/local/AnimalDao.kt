package com.example.ilink_test_application.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ilink_test_application.data.entities.Animal

@Dao
interface AnimalDao {

    @Query("SELECT * FROM animal")
    fun getAllAnimals(): LiveData<List<Animal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimal(animal: Animal)

    @Delete
    suspend fun deleteAnimal(animal: Animal)
}