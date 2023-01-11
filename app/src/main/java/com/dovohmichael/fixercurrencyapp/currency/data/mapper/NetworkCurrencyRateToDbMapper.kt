package com.dovohmichael.fixercurrencyapp.currency.data.mapper

import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkCurrencyRate


object NetworkCurrencyRateToDbMapper {

    fun map(networkCurrencyRate: NetworkCurrencyRate): DbCurrencyRate {
        return DbCurrencyRate(
            date = networkCurrencyRate.date,
            base = networkCurrencyRate.base,
            target = networkCurrencyRate.rates.keys.first(),
            timestamp = networkCurrencyRate.timestamp,
            rate = networkCurrencyRate.rates.values.first(),
            historical = networkCurrencyRate.historical,
            baseTarget = "${networkCurrencyRate.base}:${networkCurrencyRate.rates.keys.first()}:${networkCurrencyRate.date}"
            )
    }

}