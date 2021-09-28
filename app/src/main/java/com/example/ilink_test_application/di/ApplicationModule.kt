package com.example.ilink_test_application.di

import android.content.Context
import com.bumptech.glide.Glide
import com.example.ilink_test_application.data.local.AnimalDao
import com.example.ilink_test_application.data.remote.CatRemoteDataSource
import com.example.ilink_test_application.data.remote.DuckRemoteDataSource
import com.example.ilink_test_application.data.repository.AnimalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideAnimalRepository(
        remoteCatDataSource: CatRemoteDataSource,
        remoteDuckDataSource: DuckRemoteDataSource,
        localAnimalDataSource: AnimalDao,
    ) =
        AnimalRepository(remoteCatDataSource, remoteDuckDataSource, localAnimalDataSource)

    @Singleton
    @Provides
    fun provideGlideRequestManager(@ApplicationContext context: Context) = Glide.with(context)
}
