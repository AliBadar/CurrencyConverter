package com.testassignment.core.responses.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.testassignment.core.responses.exchane_rates.ExchangeRateData

class Converters {
    @TypeConverter
    fun listToJson(value: List<ExchangeRateData>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<ExchangeRateData>::class.java).toList()
}