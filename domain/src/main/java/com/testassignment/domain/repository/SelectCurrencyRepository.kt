package com.testassignment.domain.repository


import com.testassignment.core.responses.select_currency.Currency
import kotlinx.coroutines.flow.Flow

interface SelectCurrencyRepository {
    suspend fun selectCurrency(): Flow<List<Currency>>
}