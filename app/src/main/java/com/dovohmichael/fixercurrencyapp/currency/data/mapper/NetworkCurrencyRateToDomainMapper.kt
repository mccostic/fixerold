package com.dovohmichael.fixercurrencyapp.currency.data.mapper

import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrency
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkConvertedCurrency
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkCurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.domain.model.ConvertedCurrency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.CurrencyRate
import java.util.*

object NetworkCurrencyRateToDomainMapper {

    fun map(networkCurrencyRate: NetworkCurrencyRate): CurrencyRate {
        return CurrencyRate(
            date = networkCurrencyRate.date,
            base = networkCurrencyRate.base,
            target = networkCurrencyRate.rates.keys.first(),
            timestamp = networkCurrencyRate.timestamp,
            rate = networkCurrencyRate.rates.values.first(),
            historical = networkCurrencyRate.historical
        )
    }

}