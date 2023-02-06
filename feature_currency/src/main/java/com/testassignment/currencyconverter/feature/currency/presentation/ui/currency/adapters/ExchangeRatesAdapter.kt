package com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.testassignment.core.responses.exchane_rates.ExchangeRateData
import com.testassignment.currencyconverter.feature.currency.databinding.ItemCurrencyConvertedBinding
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.CurrencyFragment
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.interfaces.OnAmountChangeListener
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.interfaces.OnItemClickListener
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.utils.DrawableUtility
import javax.inject.Inject

class ExchangeRatesAdapter  @Inject constructor(

) : RecyclerView.Adapter<ExchangeRatesAdapter.CurrencyViewHolder>() {

    private var currenciesList: List<ExchangeRateData>? = mutableListOf()

    private lateinit var onItemClickListener: OnItemClickListener

    private lateinit var onAmountChangeListener: OnAmountChangeListener

    class CurrencyViewHolder private constructor(private val binding: ItemCurrencyConvertedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            content: ExchangeRateData,
            onItemClickListener: OnItemClickListener,
            onAmountChangeListener: OnAmountChangeListener
        ) {
            content?.let {currency ->
                binding.imgCountryFlag.setImageResource(DrawableUtility.getDrawableResourceByName(currency.code.lowercase(), itemView.context))
                binding.tvCurrencyCode.text = currency.code
//                binding.tvCurrencyValue.text = currency.convertedAmount
                binding.tvCurrencyValue.setText(currency.convertedAmount)
            }

//            binding.tvCurrencyValue.addTextChangedListener {
//                onAmountChangeListener.onAmountChangeListener(content, it.toString())
//            }

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(content)
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

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnAmountChangeListener(onAmountChangeListener: OnAmountChangeListener) {
        this.onAmountChangeListener = onAmountChangeListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = currenciesList?.get(position)
        item.let { it?.let { it1 -> holder.bind(it1, onItemClickListener, onAmountChangeListener) } }
    }

    override fun getItemCount(): Int {
        return currenciesList?.size ?: 0
    }
}