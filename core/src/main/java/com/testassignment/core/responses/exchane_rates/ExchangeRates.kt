package com.testassignment.core.responses.exchane_rates

import com.google.gson.annotations.SerializedName


data class ExchangeRates (

  @SerializedName("disclaimer" ) var disclaimer : String? = null,
  @SerializedName("license"    ) var license    : String? = null,
  @SerializedName("timestamp"  ) var timestamp  : Long?    = null,
  @SerializedName("base"       ) var base       : String? = null,
  @SerializedName("rates"      ) var rates      : Rates?  = Rates()

)