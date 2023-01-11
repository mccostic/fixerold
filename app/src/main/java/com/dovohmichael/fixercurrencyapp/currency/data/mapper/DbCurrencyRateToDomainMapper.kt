package com.dovohmichael.fixercurrencyapp.currency.data.mapper

import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrency
import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.domain.model.Currency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.CurrencyRate


object DbCurrencyRateToDomainMapper {

    fun map(dbCurrencyRate: DbCurrencyRate): CurrencyRate {
        return CurrencyRate(
            date = dbCurrencyRate.date,
            base = dbCurrencyRate.base,
            target = dbCurrencyRate.target,
            timestamp = dbCurrencyRate.timestamp,
            rate = dbCurrencyRate.rate,
            historical = dbCurrencyRate.historical
        )
    }

}