package com.example.ilink_test_application.data.remote

import javax.inject.Inject

class DuckRemoteDataSource @Inject constructor(
    private val duckService: DuckService
) : BaseRemoteDataSource() {

    suspend fun getRandomDuck() = getResult { duckService.getRandomDuck() }
}
