package com.testassignment.core.network.sources

import com.testassignment.core.network.ApiService
import com.testassignment.core.network.BaseDataSource
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseDataSource() {
    suspend fun getExchangeRates() = getResult {
        apiService.getExchangeRates()
    }
}