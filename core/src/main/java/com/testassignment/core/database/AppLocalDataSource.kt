package com.testassignment.core.database

import com.testassignment.core.responses.exchane_rates.ExchangeRateData
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLocalDataSource @Inject constructor(private val appDao: AppDao) {

    suspend fun addExchangeRateData(exchangeRateData: List<ExchangeRateData>) = appDao.addExchangeRateData(exchangeRateData)
//
    suspend fun getExchangeRatesData() = appDao.getExchangeRatesData()


}