package com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.uistates

import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity


sealed class ExchangeRatesMainUiState

object LoadingState : ExchangeRatesMainUiState()
data class Content(val exchangeRates: ExchangeRatesEntity) : ExchangeRatesMainUiState()
data class ErrorState(val message: String) : ExchangeRatesMainUiState()
