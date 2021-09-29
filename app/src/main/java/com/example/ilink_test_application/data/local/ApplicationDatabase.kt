package com.example.ilink_test_application.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ilink_test_application.data.entities.Animal

/**
 * Class to create and use local database for [Animal] class
 *
 * @author ilya.rakipov@gmail.com
 */
@Database(entities = [Animal::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {

    /**
     * Function to use [AnimalDao]
     */
    abstract fun animalDao(): AnimalDao

    /**
     * Static object to create and use database
     *
     * @property[instance] keep into itself instance of created database
     *
     * @author ilya.rakipov@gmail.com
     */
    companion object {

        @Volatile
        private var instance: ApplicationDatabase? = null

        /**
         * Function to get or create database instance
         *
         * @return[instance]
         */
        fun getDatabase(context: Context): ApplicationDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        /**
         * Function to create database and set it into instance
         *
         * @return[instance]
         */
        private fun buildDatabase(context: Context): ApplicationDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context,
                    ApplicationDatabase::class.java,
                    "application_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            return instance as ApplicationDatabase
        }
    }
}
