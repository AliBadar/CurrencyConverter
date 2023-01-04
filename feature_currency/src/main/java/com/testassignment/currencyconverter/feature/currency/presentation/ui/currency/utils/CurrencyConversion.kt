package com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

object CurrencyConversion {

    fun convertCurrency(amount: BigDecimal, fromRate: Double, toRate: Double): String {
        val valueInDollars = convertAnyCurrencyToDollar(amount, fromRate)
        val value = convertDollarToAnyCurrency(valueInDollars, toRate)
        return DecimalFormat("#.####").format(value.toDouble())
    }

    private fun convertAnyCurrencyToDollar(amount: BigDecimal, fromRate: Double): BigDecimal {
        val scale = 50
        return amount.divide(BigDecimal.valueOf(fromRate), scale, RoundingMode.HALF_UP)
    }

    private fun convertDollarToAnyCurrency(dollarValue: BigDecimal, toRate: Double): BigDecimal {
        return dollarValue.multiply(BigDecimal.valueOf(toRate))
    }
}