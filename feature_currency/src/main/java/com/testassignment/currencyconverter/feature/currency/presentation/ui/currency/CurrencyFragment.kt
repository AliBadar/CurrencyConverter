package com.testassignment.currencyconverter.feature.currency.presentation.ui.currency

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.testassignment.common.showSnack
import com.testassignment.core.responses.exchane_rates.ExchangeRateData
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity
import com.testassignment.core.utils.MyPreference
import com.testassignment.currencyconverter.feature.currency.databinding.FragmentCurrencyBinding
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.adapters.ExchangeRatesAdapter
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.interfaces.OnAmountChangeListener
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.interfaces.OnItemClickListener
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.uistates.Content
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.uistates.ErrorState
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.uistates.ExchangeRatesMainUiState
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.uistates.LoadingState
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.utils.DrawableUtility
import com.testassignment.currencyconverter.feature.di.inject
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrencyFragment : Fragment(), OnItemClickListener, OnAmountChangeListener {

    private lateinit var mBinding: FragmentCurrencyBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var exchangeRatesAdapter: ExchangeRatesAdapter

    private var baseCurrency = "USD"

    @Inject
    lateinit var myPreference: MyPreference

    var currenciesList: List<ExchangeRateData>? = null


    private val currencyViewModel: CurrencyViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CurrencyViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCurrencyBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValues()
        initRecyclerView()
        initObservations()
        initClickListeners()
    }

    private fun initObservations() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                currencyViewModel.exchangeRatesData.collect { exchangeRates ->
                    updateUI(exchangeRates)
                }
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("baseCurrency")
            ?.observe(viewLifecycleOwner) { baseCurrency ->
                if (baseCurrency.isNotEmpty()) {
                    this.baseCurrency = baseCurrency
                    myPreference.saveBaseCurrency(baseCurrency)
                    myPreference.saveBaseCurrencies(baseCurrency)
                    mBinding.txtBaseCurrency.text = baseCurrency
                    mBinding.imgCountryFlag.setImageResource(
                        DrawableUtility.getDrawableResourceByName(
                            myPreference.getBaseCurrency().lowercase(),
                            requireContext()
                        )
                    )
                    var amount = 1.0
                    if (mBinding.etAmount.text?.isNotEmpty() == true) {
                        amount = mBinding.etAmount.text.toString().toDouble()
                    }
                    covertCurrencies(getFocusedCurrencyAmount())
                }
            }

        mBinding.etAmount.addTextChangedListener { text ->
            var amount = 1.0
            if (text?.startsWith(".") == true){
                text.replace(0,0, "1")
            }

            if (text?.isNotEmpty() == true) {
                amount = text.toString().toDouble()
            }
            currencyViewModel.convertCurrencies(currenciesList, amount, baseCurrency)
            exchangeRatesAdapter.setCurrencyList(currenciesList)
        }
    }

    fun initClickListeners() {
        mBinding.cardSelectedCurrency.setOnClickListener {
            findNavController().navigate(
                CurrencyFragmentDirections.actionCurrencyFragmentToSelectCurrencyFragment(
                    baseCurrency
                )
            )
        }
        mBinding.btnClear.setOnClickListener {
            myPreference.clearAllPrefs()
            baseCurrency = "USD"
            currenciesList = arrayListOf()
            exchangeRatesAdapter.setCurrencyList(currenciesList)
            mBinding.etAmount.setText("")
        }
    }

    private fun updateUI(exchangeRatesMainUiState: ExchangeRatesMainUiState) {
        when (exchangeRatesMainUiState) {
            is LoadingState -> {
                mBinding.shimmerLayout.startShimmer()
            }
            is Content -> {
                mBinding.shimmerLayout.hideShimmer()
                mBinding.shimmerLayout.visibility = View.GONE
                setData(exchangeRatesMainUiState.exchangeRates)
            }
            is ErrorState -> {
                mBinding.shimmerLayout.hideShimmer()
                mBinding.shimmerLayout.visibility = View.GONE
                view?.showSnack(exchangeRatesMainUiState.message)
            }
        }
    }

    private fun setData(exchangeRates: ExchangeRatesEntity) {
        myPreference.saveTimeStamp(exchangeRates.timestamp)
        mBinding.imgCountryFlag.setImageResource(
            DrawableUtility.getDrawableResourceByName(
                myPreference.getBaseCurrency().lowercase(),
                requireContext()
            )
        )
//        currenciesList = exchangeRates?.rates

        covertCurrencies(getFocusedCurrencyAmount())
    }

    private fun covertCurrencies(amount: Double) {

        currenciesList = currencyViewModel.getSaveCurrencies()
        currencyViewModel.convertCurrencies(currenciesList, amount, baseCurrency)
        exchangeRatesAdapter.setCurrencyList(currenciesList)
    }

    private fun initRecyclerView() {
        mBinding.rvConvertedCurrencies.also {
            it.adapter = exchangeRatesAdapter
        }

        exchangeRatesAdapter.setOnItemClickListener(this)
        exchangeRatesAdapter.setOnAmountChangeListener(this)
    }

    private fun setValues(){
        baseCurrency = myPreference.getBaseCurrency()
        mBinding.txtBaseCurrency.text = baseCurrency
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onItemClick(currencyItem: ExchangeRateData) {

        myPreference.saveCurrenciesList(myPreference.getBaseCurrencies().run {
            this.remove(currencyItem.code)
            this
        })
        covertCurrencies(getFocusedCurrencyAmount())
    }

    override fun onAmountChangeListener(currencyItem: ExchangeRateData, value: String) {
        if (value.isNotEmpty()){
            baseCurrency = currencyItem.code
            covertCurrencies(value.toDouble())
        }
    }

    private fun getFocusedCurrencyAmount(): Double{
        var amount = 1.0
        if (mBinding.etAmount.text?.isNotEmpty() == true) {
            amount = mBinding.etAmount.text.toString().toDouble()
        }
        return amount
    }

}