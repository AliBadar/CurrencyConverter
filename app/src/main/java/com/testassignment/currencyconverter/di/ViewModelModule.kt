package com.testassignment.currencyconverter.di

import androidx.lifecycle.ViewModelProvider
import com.testassignment.currencyconverter.di.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/* ViewModel Map needed by ViewModelFactory */
@Module
@InstallIn(SingletonComponent::class)
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
