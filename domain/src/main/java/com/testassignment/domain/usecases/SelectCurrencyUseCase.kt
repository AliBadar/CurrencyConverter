package com.testassignment.domain.usecases

import com.testassignment.domain.repository.CurrencyConverterRepository
import com.testassignment.domain.repository.SelectCurrencyRepository
import javax.inject.Inject

class SelectCurrencyUseCase @Inject constructor(private val selectCurrencyRepository: SelectCurrencyRepository) {
    suspend operator fun invoke() = selectCurrencyRepository.selectCurrency()
}