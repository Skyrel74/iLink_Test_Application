package com.example.ilink_test_application.di

import com.example.ilink_test_application.data.remote.CatRemoteDataSource
import com.example.ilink_test_application.data.remote.CatService
import com.example.ilink_test_application.data.remote.DuckRemoteDataSource
import com.example.ilink_test_application.data.remote.DuckService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dependency Injection network module to provide instances in application dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Function to provide [Gson] to serialize objects with excluding fields without [Expose] annotation
     *
     * @return[Gson]
     */
    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    /**
     * Function to provide client for CatAPI
     *
     * @param[gson] serialization converter
     *
     * @return[CatService]
     */
    @Singleton
    @Provides
    fun provideCatService(gson: Gson): CatService =
        Retrofit.Builder()
            .baseUrl("https://thatcopy.pw/catapi/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CatService::class.java)

    /**
     * Function to provide client for DuckAPI
     *
     * @param[gson] serialization converter
     *
     * @return[DuckService]
     */
    @Singleton
    @Provides
    fun provideDuckService(gson: Gson): DuckService =
        Retrofit.Builder()
            .baseUrl("https://random-d.uk/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DuckService::class.java)

    /**
     * Function to provide remote data source of CatAPI
     *
     * @param[catService] CatAPI client
     *
     * @return[CatRemoteDataSource]
     */
    @Singleton
    @Provides
    fun provideCatRemoteDataSource(catService: CatService) =
        CatRemoteDataSource(catService)

    /**
     * Function to provide remote data source of DuckAPI
     *
     * @param[duckService] CatAPI client
     *
     * @return[DuckRemoteDataSource]
     */
    @Singleton
    @Provides
    fun provideDuckRemoteDataSource(duckService: DuckService) =
        DuckRemoteDataSource(duckService)
}
