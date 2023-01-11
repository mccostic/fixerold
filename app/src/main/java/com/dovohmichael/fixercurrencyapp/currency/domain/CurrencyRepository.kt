package com.dovohmichael.fixercurrencyapp.currency.domain


import com.dovohmichael.fixercurrencyapp.core.domain.model.Result
import com.dovohmichael.fixercurrencyapp.currency.domain.error.CurrencyError
import com.dovohmichael.fixercurrencyapp.currency.domain.model.ConvertedCurrency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.Currency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.CurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.domain.model.History

interface CurrencyRepository {

    suspend fun getCurrencies(): Result<CurrencyError, List<Currency>>


    suspend fun convert(
        from: String,
        to: String,
        amount: Double
    ): Result<CurrencyError, ConvertedCurrency>

    suspend fun getRate(
        base: String,
        target: String,
        date: String
    ): Result<CurrencyError, CurrencyRate>


    suspend fun getHistory(
        base: String,
        target: String,
        startDate: String,
        endDate:String
    ): Result<CurrencyError, History>
}

/*{
  "base": "USD",
  "date": "2022-12-31",
  "historical": true,
  "rates": {
    "GHS": 10.15039
  },
  "success": true,
  "timestamp": 1672482603
}*/