package com.example.ilink_test_application.di

import android.content.Context
import com.example.ilink_test_application.data.local.ApplicationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        ApplicationDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideAnimalDao(db: ApplicationDatabase) = db.animalDao()
}
