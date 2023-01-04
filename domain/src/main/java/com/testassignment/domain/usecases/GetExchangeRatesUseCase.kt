package com.testassignment.domain.usecases

import com.testassignment.domain.repository.CurrencyConverterRepository
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(private val currencyConverterRepository: CurrencyConverterRepository) {
    suspend operator fun invoke() = currencyConverterRepository.getExchangeRates()
}