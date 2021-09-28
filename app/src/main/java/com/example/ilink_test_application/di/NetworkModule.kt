package com.example.ilink_test_application.di

import com.example.ilink_test_application.data.remote.CatRemoteDataSource
import com.example.ilink_test_application.data.remote.CatService
import com.example.ilink_test_application.data.remote.DuckRemoteDataSource
import com.example.ilink_test_application.data.remote.DuckService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @Singleton
    @Provides
    fun provideCatService(gson: Gson): CatService =
        Retrofit.Builder()
            .baseUrl("https://thatcopy.pw/catapi/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CatService::class.java)

    @Singleton
    @Provides
    fun provideDuckService(gson: Gson): DuckService =
        Retrofit.Builder()
            .baseUrl("https://random-d.uk/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DuckService::class.java)

    @Singleton
    @Provides
    fun provideCatRemoteDataSource(catService: CatService) =
        CatRemoteDataSource(catService)

    @Singleton
    @Provides
    fun provideDuckRemoteDataSource(duckService: DuckService) =
        DuckRemoteDataSource(duckService)
}