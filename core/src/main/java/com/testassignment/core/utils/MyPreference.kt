package com.testassignment.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


class MyPreference @Inject constructor(context: Context) {

    var mContext = context

    private val BASE_CURRENCY = "my.preference.currency.key"
    private val BASE_CURRENCY_LIST = "my.preference.currency.list.key"
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

    fun getBaseCurrencies(): ArrayList<String>{
        val gson = Gson()
        val json = prefs.getString(BASE_CURRENCY_LIST, arrayListOf<String>().toString())

        // below line is to get the type of our array list.
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson<Any>(json, type) as ArrayList<String>
    }

    fun saveBaseCurrencies(base: String){
        val prefCurrencies = getBaseCurrencies()
        if (base !in prefCurrencies) {
            prefCurrencies.add(base)
        }

        saveCurrenciesList(prefCurrencies)
    }

    fun saveCurrenciesList(prefCurrencies: ArrayList<String>){
        val editor: SharedPreferences.Editor = prefs.edit()
        with(Gson()){
            val json: String = this.toJson(prefCurrencies)
            editor.putString(BASE_CURRENCY_LIST, json)
            editor.apply()
        }
    }


    fun saveTimeStamp(timeStamp: Long) {
        prefs.edit().putLong(TIME_STAMP, timeStamp).apply()
    }

    fun getTimeStamp(): Long {
        return prefs.getLong(TIME_STAMP, 0)
    }

    fun clearAllPrefs(){
        prefs.edit().clear().apply()
    }
}