package com.testassignment.domain.usecases

import com.testassignment.domain.repository.CurrencyConverterRepository
import javax.inject.Inject
import javax.inject.Named

class GetExchangeRatesUseCaseTest @Inject constructor(@Named("test") private val currencyConverterRepository: CurrencyConverterRepository) {

    suspend operator fun invoke() = currencyConverterRepository.getExchangeRates()
}