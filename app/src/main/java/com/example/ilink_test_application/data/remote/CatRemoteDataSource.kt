package com.example.ilink_test_application.data.remote

import javax.inject.Inject

class CatRemoteDataSource @Inject constructor(
    private val catService: CatService,
) : BaseRemoteDataSource() {

    suspend fun getRandomCat() = getResult { catService.getRandomCat() }
}
