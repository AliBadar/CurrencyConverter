package com.testassignment.currencyconverter.di


import com.testassignment.domain.repository.CurrencyConverterRepository
import com.testassignment.domain.repository.SelectCurrencyRepository
import com.testassignment.domain.usecases.GetExchangeRatesUseCase
import com.testassignment.domain.usecases.SelectCurrencyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetExchangeRatesUseCase(currencyConverterRepository: CurrencyConverterRepository): GetExchangeRatesUseCase = GetExchangeRatesUseCase(currencyConverterRepository)

    @Provides
    @Singleton
    fun provideSelectCurrencyUseCase(selectCurrencyRepository: SelectCurrencyRepository): SelectCurrencyUseCase = SelectCurrencyUseCase(selectCurrencyRepository)
}
