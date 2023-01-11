package com.dovohmichael.fixercurrencyapp.currency.domain.model

data class CurrencyRate(var base:String, var date:String, var historical:Boolean,var timestamp:Long, var target:String, var rate:Double)

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