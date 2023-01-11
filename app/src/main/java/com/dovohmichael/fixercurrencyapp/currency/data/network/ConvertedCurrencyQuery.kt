package com.dovohmichael.fixercurrencyapp.currency.data.network

import com.squareup.moshi.Json

data class ConvertedCurrencyQuery(
    @Json(name = "amount")
    var amount:Double,
    @Json(name = "from")
    var from:String,
    @Json(name = "to")
    var to:String,

)