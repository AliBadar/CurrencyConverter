package com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.interfaces

import com.testassignment.core.responses.exchane_rates.ExchangeRateData
import com.testassignment.core.responses.select_currency.Currency


interface OnAmountChangeListener {
    fun onAmountChangeListener(currencyItem: ExchangeRateData, value: String)
}