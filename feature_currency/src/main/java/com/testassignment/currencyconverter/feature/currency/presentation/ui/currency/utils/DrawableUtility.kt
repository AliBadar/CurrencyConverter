package com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.utils

import android.content.Context
import com.testassignment.currencyconverter.feature.currency.R

object DrawableUtility {

    fun getDrawableResourceByName(name: String, context: Context?): Int {
        var imageResource = context!!.resources.getIdentifier("usd_$name", "drawable", context.packageName)
        if (imageResource == 0){
            imageResource = context!!.resources.getIdentifier("ic_placeholder", "drawable", context.packageName)
        }

        return imageResource
    }
}