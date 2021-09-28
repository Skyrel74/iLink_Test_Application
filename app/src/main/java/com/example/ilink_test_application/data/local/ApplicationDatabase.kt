package com.example.ilink_test_application.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ilink_test_application.data.entities.Animal

@Database(entities = [Animal::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun animalDao(): AnimalDao

    companion object {
        @Volatile
        private var instance: ApplicationDatabase? = null

        fun getDatabase(appContext: Context): ApplicationDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(appContext).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context): ApplicationDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    appContext,
                    ApplicationDatabase::class.java,
                    "application_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            return instance as ApplicationDatabase
        }
    }
}
