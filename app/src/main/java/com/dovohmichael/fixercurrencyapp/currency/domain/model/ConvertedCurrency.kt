package com.dovohmichael.fixercurrencyapp.currency.domain.model

data class ConvertedCurrency(var baseAmount:Double,var convertedAmount:Double,var baseCurrency:String,val targetCurrency:String)