package com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.testassignment.core.responses.select_currency.Currency
import com.testassignment.currencyconverter.feature.currency.R
import com.testassignment.currencyconverter.feature.currency.databinding.FragmentCurrencyBinding
import com.testassignment.currencyconverter.feature.currency.databinding.FragmentSelectCurrencyBinding
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.CurrencyViewModel
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.adapters.SelectCurrencyAdapter
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.interfaces.OnItemClickListener
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.uistates.Content
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.uistates.CurrenciesMainUiState
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.uistates.LoadingState
import com.testassignment.currencyconverter.feature.di.inject
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCurrencyFragment : Fragment(), OnItemClickListener {

    private lateinit var mBinding: FragmentSelectCurrencyBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var selectCurrencyAdapter: SelectCurrencyAdapter

    private var baseCurrency = ""

    private val selectCurrencyViewModel: SelectCurrencyViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SelectCurrencyViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSelectCurrencyBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseCurrency = arguments?.getString("baseCurrency").toString()
        initToolBar()
        initRecyclerView()
        initObservations()
    }

    private fun initToolBar(){
        mBinding.myToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_down)
            title = "Select Currency"
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initRecyclerView(){
        mBinding.rvSelectCurrency.also {
            it.adapter = selectCurrencyAdapter
        }
    }

    private fun initObservations() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                selectCurrencyViewModel.selectCurrencyData.collect { selectCurrency ->
                    updateUI(selectCurrency)
                }
            }
        }
    }


    private fun updateUI(currenciesMainUiState: CurrenciesMainUiState) {
        when(currenciesMainUiState){
            is LoadingState -> {
//                showLoader()
            }
            is Content -> {
                setData(currenciesMainUiState.currenciesList)
            }
        }

    }

    private fun setData(currenciesList: List<Currency>) {
        selectCurrencyAdapter.setCurrencyList(currenciesList, baseCurrency, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onItemClick(baseCurrency: Currency) {
        if (!baseCurrency.isSelected){
            findNavController().previousBackStackEntry?.savedStateHandle?.set("baseCurrency", baseCurrency.code)
            findNavController().popBackStack()
        }
    }

}