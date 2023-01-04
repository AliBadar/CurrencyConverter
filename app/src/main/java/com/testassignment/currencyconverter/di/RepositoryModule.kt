package com.testassignment.currencyconverter.di



import android.content.Context
import com.testassignment.core.database.AppLocalDataSource
import com.testassignment.core.network.sources.ApiRemoteDataSource
import com.testassignment.core.utils.MyPreference
import com.testassignment.data.mapper.ExchangeRatesMapper
import com.testassignment.data.repository.CurrenciesConverterRepositoryImp
import com.testassignment.data.repository.SelectCurrencyRepositoryImp
import com.testassignment.domain.repository.CurrencyConverterRepository
import com.testassignment.domain.repository.SelectCurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideExchangeRatesRepository(apiRemoteDataSource: ApiRemoteDataSource, appLocalDataSource: AppLocalDataSource, exchangeRatesMapper: ExchangeRatesMapper, myPreference: MyPreference): CurrencyConverterRepository {
        return CurrenciesConverterRepositoryImp(apiRemoteDataSource, appLocalDataSource, exchangeRatesMapper, myPreference)
    }

    @Singleton
    @Provides
    fun provideGetCurrenciesRepository(): SelectCurrencyRepository {
        return SelectCurrencyRepositoryImp()
    }
}
