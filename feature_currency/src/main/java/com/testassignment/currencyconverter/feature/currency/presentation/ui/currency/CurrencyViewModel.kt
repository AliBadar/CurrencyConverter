package com.testassignment.currencyconverter.feature.currency.presentation.ui.currency

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testassignment.core.network.Resource
import com.testassignment.core.responses.exchane_rates.ExchangeRateData
import com.testassignment.core.utils.MyPreference
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.uistates.Content
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.uistates.ErrorState
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.uistates.ExchangeRatesMainUiState
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.uistates.LoadingState
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.utils.CurrencyConversion
import com.testassignment.domain.usecases.GetExchangeRatesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase
) : ViewModel() {
    private val _exchangeRatesData = MutableStateFlow<ExchangeRatesMainUiState>(LoadingState)
    val exchangeRatesData = _exchangeRatesData.asStateFlow()

    init {
        getExchangeRates()
    }

    private fun getExchangeRates() {
        viewModelScope.launch {
            _exchangeRatesData.value = LoadingState

            getExchangeRatesUseCase().collect { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        val data = resource.data
                        data?.let { exchangeRates ->
                            _exchangeRatesData.value = Content(exchangeRates)
                        }
                    }
                    Resource.Status.ERROR -> {
                        _exchangeRatesData.value = ErrorState(resource.message.toString())
                    }
                    Resource.Status.LOADING -> {}
                }
            }
        }

    }

    fun convertCurrencies(
        currenciesList: List<ExchangeRateData>?,
        amount: Double,
        baseCurrency: String
    ) {

        val focusedCurrency = currenciesList?.singleOrNull { it.code == baseCurrency }
//
        val fromRate = focusedCurrency?.rate
        amount?.let { amount ->
            currenciesList
                ?.filter { it.code != baseCurrency }
                ?.forEach { exchangeRate ->
                    val toRate = exchangeRate.rate
                    fromRate?.let { fronmRate ->

                        val conversionValue = CurrencyConversion.convertCurrency(
                            BigDecimal(amount), fromRate, toRate!!
                        )
                        exchangeRate.convertedAmount = conversionValue.toDouble()
                    }
                }
        }
    }
}