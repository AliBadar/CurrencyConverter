package com.testassignment.core.responses.exchane_rates

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rates")
data class ExchangeRateData(

    @PrimaryKey
    @ColumnInfo(name = "code")
    var code : String = "",

    @ColumnInfo(name = "rate")
    var rate : Double? = null,

    var convertedAmount : Double ?= null
)


