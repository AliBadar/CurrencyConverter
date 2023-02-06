package com.testassignment.data.repository

import com.testassignment.core.database.AppLocalDataSource
import com.testassignment.core.network.Resource
import com.testassignment.core.network.sources.ApiRemoteDataSource
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity
import com.testassignment.core.utils.MyPreference
import com.testassignment.core.utils.Utils.isNetworkAvailable
import com.testassignment.data.mapper.ExchangeRatesMapper
import com.testassignment.domain.repository.CurrencyConverterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CurrenciesConverterRepositoryTestImp @Inject constructor(
    private val apiRemoteDataSource: ApiRemoteDataSource,
    private val appLocalDataSource: AppLocalDataSource,
    private val exchangeRatesMapper: ExchangeRatesMapper,
    private val myPreference: MyPreference
): CurrencyConverterRepository {
    override suspend fun getExchangeRates(): Flow<Resource<ExchangeRatesEntity>> {
        return flow{
            if (myPreference.mContext.isNetworkAvailable()){
                apiRemoteDataSource.getExchangeRates().apply {
                    val exchangeRates = exchangeRatesMapper.mapToEntity(this.data)
                    emit(Resource(this.status, exchangeRates, null))
                }
            }
        }
    }

}