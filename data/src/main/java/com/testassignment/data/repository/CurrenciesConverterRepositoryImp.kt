package com.testassignment.data.repository

import androidx.annotation.WorkerThread
import com.testassignment.core.database.AppLocalDataSource
import com.testassignment.core.network.Resource
import com.testassignment.core.network.sources.ApiRemoteDataSource
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity
import com.testassignment.core.utils.DateUtility
import com.testassignment.core.utils.MyPreference
import com.testassignment.core.utils.Utils.isNetworkAvailable
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
            val localExchangeData = appLocalDataSource.getExchangeRatesData()
            if (myPreference.mContext.isNetworkAvailable() && (DateUtility.getTimeDifference(previousTimeStamp = myPreference.getTimeStamp()) > 30 || localExchangeData.isNullOrEmpty())) {
                if (myPreference.mContext.isNetworkAvailable()) {
                    apiRemoteDataSource.getExchangeRates().apply {
                        this.data?.timestamp = System.currentTimeMillis()
                        this.data?.rates?.currencies?.let {
                            appLocalDataSource.addExchangeRateData(
                                it
                            )
                        }
                        val mapExchangeRates = exchangeRatesMapper.mapToEntity(this.data)
                        emit(Resource(this.status, mapExchangeRates, this.message))
                    }
                }else{
                    emit(Resource(Resource.Status.ERROR, null, "No Internet Found"))
                }

            } else if (localExchangeData?.isNotEmpty() == true) {

                emit(
                    Resource.success(
                        ExchangeRatesEntity(
                            myPreference.getBaseCurrency(),
                            myPreference.getTimeStamp(),
                            localExchangeData
                        )
                    )
                )
            }else{
                emit(Resource(Resource.Status.ERROR, null, NETWORK_OR_DATA_NOT_AVAILABLE))
            }
        }
    }

    companion object {
        const val NETWORK_OR_DATA_NOT_AVAILABLE = "Network is not available and no local data found."
    }

}