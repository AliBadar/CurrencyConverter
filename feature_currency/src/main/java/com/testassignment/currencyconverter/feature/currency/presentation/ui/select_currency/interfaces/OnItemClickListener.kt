package com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.interfaces

import com.testassignment.core.responses.select_currency.Currency


interface OnItemClickListener {
    fun onItemClick(baseCurrency: Currency)
}