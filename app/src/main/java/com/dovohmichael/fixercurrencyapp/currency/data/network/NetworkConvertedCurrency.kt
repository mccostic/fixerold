package com.dovohmichael.fixercurrencyapp.currency.data.network

import com.squareup.moshi.Json

data class NetworkConvertedCurrency(
    @Json(name = "query")
    var query :ConvertedCurrencyQuery,
    @Json(name = "success")
    var success:Boolean,
    @Json(name = "result")
    var result:Double,
    @Json(name = "date")
    var date:String
)