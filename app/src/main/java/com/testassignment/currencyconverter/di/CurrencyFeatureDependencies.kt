package com.testassignment.currencyconverter.di


import com.testassignment.domain.usecases.GetExchangeRatesUseCase
import com.testassignment.domain.usecases.SelectCurrencyUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CurrencyFeatureDependencies {
    fun getCurrenciesUseCase(): GetExchangeRatesUseCase
    fun selectCurrenciesUseCase(): SelectCurrencyUseCase
}