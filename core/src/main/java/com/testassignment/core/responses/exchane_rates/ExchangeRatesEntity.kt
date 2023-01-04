package com.testassignment.core.responses.exchane_rates

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.testassignment.core.responses.converters.Converters
import com.testassignment.core.responses.exchane_rates.ExchangeRateData

data class ExchangeRatesEntity(

  var base: String = "",

  var timestamp: Long,

  var rates: List<ExchangeRateData> ?= null,
)