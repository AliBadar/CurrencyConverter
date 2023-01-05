package com.testassignment.core.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


class MyPreference @Inject constructor(context: Context) {

    var mContext = context

    private val BASE_CURRENCY = "my.preference.currency.key"
    private val TIME_STAMP = "my.preference.timestamp.key"


    private val prefs = context.getSharedPreferences(
        "com.testassignment.currencyconverter.my.shared.preferences",
        Context.MODE_PRIVATE
    )

    fun saveBaseCurrency(base: String) {
        prefs.edit().putString(BASE_CURRENCY, base).apply()
    }

    fun getBaseCurrency(): String {
        return prefs.getString(BASE_CURRENCY, "USD").toString()
    }

    fun saveTimeStamp(timeStamp: Long) {
        prefs.edit().putLong(TIME_STAMP, timeStamp).apply()
    }

    fun getTimeStamp(): Long {
        return prefs.getLong(TIME_STAMP, 0)
    }
}