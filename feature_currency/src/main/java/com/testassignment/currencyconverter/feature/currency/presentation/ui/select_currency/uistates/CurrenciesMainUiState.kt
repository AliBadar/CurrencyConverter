package com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.uistates

import com.testassignment.core.responses.select_currency.Currency


sealed class CurrenciesMainUiState

object LoadingState : CurrenciesMainUiState()
data class Content(val currenciesList: List<Currency>) : CurrenciesMainUiState()
