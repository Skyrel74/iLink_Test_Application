package com.example.ilink_test_application.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.ilink_test_application.data.local.AnimalDao
import com.example.ilink_test_application.data.local.ApplicationDatabase
import com.example.ilink_test_application.data.remote.CatRemoteDataSource
import com.example.ilink_test_application.data.remote.DuckRemoteDataSource
import com.example.ilink_test_application.data.repository.AnimalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency Injection core module to provide instances in application dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    /**
     * Function to provide [AnimalRepository]
     *
     * @param[remoteCatDataSource] provides remote data source of CatAPI
     * @param[remoteDuckDataSource] provides remote data source of DuckAPI
     * @param[localAnimalDataSource] provides local data source of [ApplicationDatabase]
     *
     * @return[AnimalRepository]
     */
    @Singleton
    @Provides
    fun provideAnimalRepository(
        remoteCatDataSource: CatRemoteDataSource,
        remoteDuckDataSource: DuckRemoteDataSource,
        localAnimalDataSource: AnimalDao,
    ) =
        AnimalRepository(remoteCatDataSource, remoteDuckDataSource, localAnimalDataSource)

    /**
     * Function to provide [RequestManager]
     *
     * @param[context] application context
     *
     * @return[RequestManager]
     */
    @Singleton
    @Provides
    fun provideGlideRequestManager(@ApplicationContext context: Context) = Glide.with(context)
}
