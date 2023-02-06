package com.testassignment.data.mapper

import com.testassignment.core.responses.exchane_rates.ExchangeRates
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity


import javax.inject.Inject

open class ExchangeRatesMapper  @Inject constructor()  : Mapper<ExchangeRatesEntity, ExchangeRates?> {

    override fun mapToEntity(type: ExchangeRates?): ExchangeRatesEntity {
        return ExchangeRatesEntity(
            base = type?.base.toString(),
            timestamp = type?.timestamp?:0L,
            rates = type?.rates?.currencies ?: listOf(),
        )
   }
}
