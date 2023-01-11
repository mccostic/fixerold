package com.dovohmichael.fixercurrencyapp.data.factory

import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.domain.model.CurrencyRate

object CurrencyRateTestFactory {

    fun makeCurrencyRate(
         base:String,  date:String,  historical:Boolean, timestamp:Long,  target:String,  rate:Double
    ): CurrencyRate {
        return CurrencyRate(base=base, date=date, historical=historical, timestamp = timestamp, target = target, rate = rate)
    }

    fun makeDbCurrencyRate(
         base:String,
         date:String,
         historical:Boolean,
         timestamp:Long,
         target:String,
         rate:Double
    ): DbCurrencyRate{
        return DbCurrencyRate(base = base, date =  date, historical = historical, target =  target, timestamp = timestamp, rate = rate, baseTarget = "")
    }
}