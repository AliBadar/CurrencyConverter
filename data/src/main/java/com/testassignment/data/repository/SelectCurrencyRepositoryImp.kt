package com.testassignment.data.repository

import android.content.Context
import com.testassignment.core.database.AppLocalDataSource
import com.testassignment.core.network.Resource
import com.testassignment.core.network.sources.ApiRemoteDataSource
import com.testassignment.core.responses.select_currency.Currency
import com.testassignment.data.R
import com.testassignment.data.mapper.ExchangeRatesMapper
import com.testassignment.domain.models.Currencies
import com.testassignment.domain.repository.SelectCurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SelectCurrencyRepositoryImp @Inject constructor(
    appLocalDataSource: AppLocalDataSource
) : SelectCurrencyRepository {

    override suspend fun selectCurrency(): Flow<List<Currency>> {
        val currencies = Currencies.getCurrencies()
        return flow {
            emit(currencies)
        }
    }


}