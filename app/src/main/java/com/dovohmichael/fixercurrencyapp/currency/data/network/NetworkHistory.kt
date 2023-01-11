package com.dovohmichael.fixercurrencyapp.currency.data.network

import com.squareup.moshi.Json

data class NetworkHistory (  @Json(name = "base")
                             var base :String,

                             @Json(name = "end_date")
                             var endDate:String,

                             @Json(name = "start_date")
                             var startDate:String,

                             @Json(name = "timeseries")
                             var timeseries :Boolean,

                             @Json(name = "rates")
                             var rates:Map<String,Map<String,Double>>,

                             @Json(name = "success")
                             var success:Boolean,
)
/*{
  "base": "USD",
  "end_date": "2022-12-31",
  "rates": {
    "2022-12-25": {
      "GHS": 9.999877
    },
    "2022-12-26": {
      "GHS": 9.999649
    },
    "2022-12-27": {
      "GHS": 10.250514
    },
    "2022-12-28": {
      "GHS": 9.749849
    },
    "2022-12-29": {
      "GHS": 10.000111
    },
    "2022-12-30": {
      "GHS": 10.15039
    },
    "2022-12-31": {
      "GHS": 10.15039
    }
  },
  "start_date": "2022-12-25",
  "success": true,
  "timeseries": true
}*/