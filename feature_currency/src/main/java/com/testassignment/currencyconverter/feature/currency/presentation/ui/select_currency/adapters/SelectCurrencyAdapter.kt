package com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testassignment.core.responses.select_currency.Currency
import com.testassignment.currencyconverter.feature.currency.databinding.ItemCurrencySelectedBinding
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.utils.DrawableUtility
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.interfaces.OnItemClickListener
import javax.inject.Inject

class SelectCurrencyAdapter  @Inject constructor(

) : RecyclerView.Adapter<SelectCurrencyAdapter.CurrencyViewHolder>() {

    private var currenciesList: List<Currency> = mutableListOf()

    private var baseCurrency = ""

    private lateinit var onItemClickListener: OnItemClickListener

    class CurrencyViewHolder private constructor(private val binding: ItemCurrencySelectedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            content: Currency,
            baseCurrency: String,
            onItemClickListener: OnItemClickListener
        ) {

            content?.let {currency ->
                currency.code?.let {
                    DrawableUtility.getDrawableResourceByName(
                        it.lowercase(), itemView.context)
                }?.let { binding.imgCountryFlag.setImageResource(it) }
                binding.tvCurrencyCode.text = currency.name
//                if (currency.code.equals(baseCurrency)){
//                    binding.imgCheck.visibility = View.VISIBLE
//                }else{
//                    binding.imgCheck.visibility = View.INVISIBLE
//                }

                binding.imgCheck.visibility = if (currency.isSelected) {
                    View.VISIBLE
                }else{
                    View.INVISIBLE
                }

                itemView.setOnClickListener {
                    onItemClickListener.onItemClick(currency)
                }
            }


        }

        companion object {

            fun from(parent: ViewGroup): CurrencyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCurrencySelectedBinding.inflate(layoutInflater, parent, false)
                return CurrencyViewHolder(binding)
            }

        }
    }

    fun setCurrencyList(
        currenciesList: List<Currency>,
        baseCurrency: String,
        onItemClickListener: OnItemClickListener
    ) {
        this.currenciesList = currenciesList
        this.baseCurrency = baseCurrency
        this.onItemClickListener = onItemClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = currenciesList[position]
        item.let { holder.bind(it, baseCurrency, onItemClickListener) }
    }

    override fun getItemCount(): Int {
        return currenciesList.size
    }
}