package com.example.ilink_test_application.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ilink_test_application.data.entities.Animal

/**
 * Data Access Object for managing data of [Animal] in local data source as database
 *
 * @author ilya.rakipov@gmail.com
 */
@Dao
interface AnimalDao {

    /**
     * Function to get all fields of database
     */
    @Query("SELECT * FROM animal")
    fun getAllAnimals(): LiveData<List<Animal>>

    /**
     * Function to get to know if animal in table
     */
    @Query("SELECT * FROM animal WHERE url LIKE :url")
    fun isFavorite(url: String): List<Animal>

    /**
     * Function to insert @param[animal] in database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimal(animal: Animal)

    /**
     * Function to delete @param[animal] from database
     */
    @Delete
    suspend fun deleteAnimal(animal: Animal)
}
