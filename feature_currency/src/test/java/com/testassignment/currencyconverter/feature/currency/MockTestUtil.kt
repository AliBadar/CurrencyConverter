package com.testassignment.currencyconverter.feature.currency

import com.testassignment.core.responses.exchane_rates.ExchangeRateData
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity
import com.testassignment.core.responses.select_currency.Currency

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

        fun getBaseCurrenciesList(): ArrayList<String> {
            return arrayListOf("USD", "AED", "INR")
        }

        fun getCurrencyList(): List<Currency>{
            var currencyList = (0 until 5).map {
                Currency("USD", "United States of America")
            }
            return currencyList
        }

        fun getErrorMessage(): String{
            return "Error Message"
        }
    }
}