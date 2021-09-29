package com.example.ilink_test_application.di

import android.content.Context
import com.example.ilink_test_application.data.local.AnimalDao
import com.example.ilink_test_application.data.local.ApplicationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency Injection database module to provide instances in application dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Function to provide database [ApplicationDatabase.instance]
     *
     * @param[context] application context
     *
     * @return[ApplicationDatabase.instance]
     */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        ApplicationDatabase.getDatabase(context)

    /**
     * Function to provide [AnimalDao]
     *
     * @param[db] database [ApplicationDatabase.instance]
     *
     * @return[AnimalDao]
     */
    @Singleton
    @Provides
    fun provideAnimalDao(db: ApplicationDatabase) = db.animalDao()
}
