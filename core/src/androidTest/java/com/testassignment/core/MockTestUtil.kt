package com.testassignment.core

import com.testassignment.core.responses.exchane_rates.ExchangeRateData
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity
import com.testassignment.core.responses.select_currency.Currency

class MockTestUtil {
    companion object {

        fun getExchangeRatesList(): List<ExchangeRateData> {
            val exchangeRateList = (0 until 5).map {it ->
                when(it){
                    0 -> ExchangeRateData("USD", 1.0, "1")
                    1 -> ExchangeRateData("AED", 3.67282, "1")
                    2 -> ExchangeRateData("AFN", 88.874169, "1")
                    3 -> ExchangeRateData("ALL", 106.548065, "1")
                    else -> {
                        ExchangeRateData("AMD", 390.760044, "1")
                    }
                }

            }
            return exchangeRateList
        }
    }
}