package com.dovohmichael.fixercurrencyapp.currency.domain.model

data class History(var base:String,
                   var startDate:String,
                   var timeseries:Boolean,
                   var target:String,
                   var rates:Map<String,Map<String,Double>>)

//{
//    "base": "USD",
//    "date": "2022-12-31",
//    "historical": true,
//    "rates": {
//    "GHS": 10.15039
//},
//    "success": true,
//    "timestamp": 1672482603
//}