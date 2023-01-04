package com.testassignment.currencyconverter.feature.di

import android.content.Context
import com.testassignment.currencyconverter.di.CurrencyFeatureDependencies
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.CurrencyFragment
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.SelectCurrencyFragment
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.android.EntryPointAccessors

@Component(
    dependencies = [CurrencyFeatureDependencies::class],
    modules = [
        CurrencyFeatureModule::class
    ]
)
interface CurrencyFeatureComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            dependencies: CurrencyFeatureDependencies
        ): CurrencyFeatureComponent
    }

    fun inject(fragment: CurrencyFragment)
    fun inject(fragment: SelectCurrencyFragment)

}

internal fun CurrencyFragment.inject() {
    DaggerCurrencyFeatureComponent.factory().create(
        requireActivity(),
        EntryPointAccessors.fromApplication(
            requireContext().applicationContext,
            CurrencyFeatureDependencies::class.java
    )
    ).inject(this)
}

internal fun SelectCurrencyFragment.inject() {
    DaggerCurrencyFeatureComponent.factory().create(
        requireActivity(),
        EntryPointAccessors.fromApplication(
            requireContext().applicationContext,
            CurrencyFeatureDependencies::class.java
        )
    ).inject(this)
}
