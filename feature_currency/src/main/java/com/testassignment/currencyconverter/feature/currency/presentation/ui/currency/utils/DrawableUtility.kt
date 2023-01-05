package com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.utils

import android.content.Context

object DrawableUtility {

    fun getDrawableResourceByName(name: String, context: Context?): Int {
        return context!!.resources.getIdentifier("usd_$name", "drawable", context.packageName)
    }
}