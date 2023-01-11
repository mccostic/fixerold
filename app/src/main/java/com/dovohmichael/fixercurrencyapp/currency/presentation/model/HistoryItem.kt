package com.dovohmichael.fixercurrencyapp.currency.presentation.model

data class HistoryItem (var base:String,
                        var date:String,
                        var timeseries:Boolean,
                        var target:String?,
                        var rate:Float)