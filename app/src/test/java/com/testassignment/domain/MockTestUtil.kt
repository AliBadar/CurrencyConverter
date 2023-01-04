package com.testassignment.domain

import com.testassignment.core.responses.exchane_rates.ExchangeRateData
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity

class MockTestUtil {
    companion object {
        fun createExchangeRatesEntity(): ExchangeRatesEntity {
            return ExchangeRatesEntity(base = "USD", timestamp = 1672824807975,  rates = getExchangeRatesList())
        }

        private fun getExchangeRatesList(): List<ExchangeRateData> ?{
            var exchangeRateList = (0 until 5).map {
                ExchangeRateData("USD", 1.0, "1")
            }
            return exchangeRateList
        }

        fun getErrorMessage(): String{
            return "Error Message"
        }
    }
}