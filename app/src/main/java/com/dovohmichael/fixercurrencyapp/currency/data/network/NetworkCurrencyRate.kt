package com.dovohmichael.fixercurrencyapp.currency.data.network

import com.squareup.moshi.Json

data class NetworkCurrencyRate(
    @Json(name = "base")
    var base :String,

    @Json(name = "date")
    var date:String,

    @Json(name = "historical")
    var historical :Boolean,

    @Json(name = "rates")
    var rates:Map<String,Double>,

    @Json(name = "success")
    var success:Boolean,

    @Json(name = "timestamp")
    var timestamp :Long,




)

/*

{
  "base": "USD",
  "date": "2022-12-31",
  "historical": true,
  "rates": {
    "GHS": 10.15039
  },
  "success": true,
  "timestamp": 1672482603
}*/