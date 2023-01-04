package com.testassignment.data.repository

import androidx.annotation.WorkerThread
import com.testassignment.core.database.AppLocalDataSource
import com.testassignment.core.network.Resource
import com.testassignment.core.network.sources.ApiRemoteDataSource
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity
import com.testassignment.core.utils.MyPreference
import com.testassignment.data.mapper.ExchangeRatesMapper
import com.testassignment.domain.repository.CurrencyConverterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrenciesConverterRepositoryImp @Inject constructor(
    private val apiRemoteDataSource: ApiRemoteDataSource,
    private val appLocalDataSource: AppLocalDataSource,
    private val exchangeRatesMapper: ExchangeRatesMapper,
    private val myPreference: MyPreference
) :
    CurrencyConverterRepository {
    @WorkerThread
    override suspend fun getExchangeRates(): Flow<Resource<ExchangeRatesEntity>> {
        return flow {
            if (myPreference.getTimeStamp() > 0){
                val localExchangeData = appLocalDataSource.getExchangeRatesData()
                if (localExchangeData.isNullOrEmpty()){
                    apiRemoteDataSource.getExchangeRates().apply {
                        this.data?.rates?.currencies?.let { appLocalDataSource.addExchangeRateData(it) }
                        val mapExchangeRates = exchangeRatesMapper.mapToEntity(this.data)
                        emit(Resource(this.status, mapExchangeRates, this.message))
                    }
                }else{
                    emit(Resource.success(ExchangeRatesEntity(myPreference.getBaseCurrency(), myPreference.getTimeStamp(), localExchangeData)))
                }
            }else{
                val localExchangeData = appLocalDataSource.getExchangeRatesData()
                if (localExchangeData.isNullOrEmpty()){
                    apiRemoteDataSource.getExchangeRates().apply {
                        this.data?.rates?.currencies?.let { appLocalDataSource.addExchangeRateData(it) }
                        val mapExchangeRates = exchangeRatesMapper.mapToEntity(this.data)
                        emit(Resource(this.status, mapExchangeRates, this.message))
                    }
                }else{
                    emit(Resource.success(ExchangeRatesEntity(myPreference.getBaseCurrency(), myPreference.getTimeStamp(), localExchangeData)))
                }
            }

        }
    }

}