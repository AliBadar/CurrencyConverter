package com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testassignment.core.responses.exchane_rates.ExchangeRateData
import com.testassignment.currencyconverter.feature.currency.databinding.ItemCurrencyConvertedBinding
import javax.inject.Inject

class ExchangeRatesAdapter  @Inject constructor(

) : RecyclerView.Adapter<ExchangeRatesAdapter.CurrencyViewHolder>() {

    private var currenciesList: List<ExchangeRateData>? = mutableListOf()

    class CurrencyViewHolder private constructor(private val binding: ItemCurrencyConvertedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            content: ExchangeRateData,
        ) {

            content?.let {currency ->
                binding.tvCurrencyCode.text = currency.code
                binding.tvCurrencyValue.text = currency.convertedAmount.toString()
            }
        }

        companion object {

            fun from(parent: ViewGroup): CurrencyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCurrencyConvertedBinding.inflate(layoutInflater, parent, false)
                return CurrencyViewHolder(binding)
            }

        }
    }

    fun setCurrencyList(
        currenciesList: List<ExchangeRateData>?
    ) {
        this.currenciesList = currenciesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = currenciesList?.get(position)
        item.let { it?.let { it1 -> holder.bind(it1) } }
    }

    override fun getItemCount(): Int {
        return currenciesList?.size ?: 0
    }
}