package com.testassignment.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.testassignment.core.responses.converters.Converters
import com.testassignment.core.responses.exchane_rates.ExchangeRateData
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity

/**
 * SQLite Database for storing the articles.
 */
@Database(
    entities = [ExchangeRateData::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun citiesDao(): AppDao
}