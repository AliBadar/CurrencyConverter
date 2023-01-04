package com.testassignment.currencyconverter.feature.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testassignment.currencyconverter.di.ViewModelKey
import com.testassignment.currencyconverter.di.ViewModelFactory
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.CurrencyViewModel
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.SelectCurrencyViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ViewModelComponent::class)
abstract class CurrencyFeatureModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel::class)
    internal abstract fun bindCurrencyViewModel(viewModel: CurrencyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectCurrencyViewModel::class)
    internal abstract fun bindSelectCurrencyViewModel(viewModel: SelectCurrencyViewModel): ViewModel

}
