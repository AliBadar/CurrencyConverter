package com.testassignment.domain.repository


import com.testassignment.core.network.Resource
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity
import kotlinx.coroutines.flow.Flow

interface CurrencyConverterRepository {
    suspend fun getExchangeRates(): Flow<Resource<ExchangeRatesEntity>>

}