package com.dovohmichael.fixercurrencyapp.currency.data.network

import com.squareup.moshi.Json

data class NetworkCurrencies(
    @Json(name = "symbols")
    var list :Map<String,String>,
    @Json(name = "success")
    var success:Boolean)