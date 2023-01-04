package com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.uistates.Content
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.uistates.CurrenciesMainUiState
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.uistates.LoadingState
import com.testassignment.domain.usecases.SelectCurrencyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCurrencyViewModel @Inject constructor(
    private val selectCurrencyUseCase: SelectCurrencyUseCase
): ViewModel() {

    private val _selectCurrencyData = MutableStateFlow<CurrenciesMainUiState>(LoadingState)
    val selectCurrencyData = _selectCurrencyData.asStateFlow()

    init {
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch {
            selectCurrencyUseCase().collect{currenciesList ->
                _selectCurrencyData.value = Content(currenciesList)
            }
        }
    }
}