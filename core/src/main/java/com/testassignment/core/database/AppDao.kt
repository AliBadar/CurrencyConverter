package com.testassignment.core.database

import androidx.room.*
import com.testassignment.core.responses.exchane_rates.ExchangeRateData
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExchangeRateData(exchangeRateData: List<ExchangeRateData>)

    @Query("SELECT * FROM exchange_rates")
    suspend fun getExchangeRatesData(): List<ExchangeRateData>

}