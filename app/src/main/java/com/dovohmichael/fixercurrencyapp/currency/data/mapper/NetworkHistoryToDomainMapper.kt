package com.dovohmichael.fixercurrencyapp.currency.data.mapper

import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrency
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkConvertedCurrency
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkCurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkHistory
import com.dovohmichael.fixercurrencyapp.currency.domain.model.ConvertedCurrency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.CurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.domain.model.History
import java.util.*

object NetworkHistoryToDomainMapper {

    fun map(networkHistory: NetworkHistory): History {
        return History(
            startDate = networkHistory.startDate,
            base = networkHistory.base,
            target = "networkHistory",
            timeseries = networkHistory.timeseries,
            rates = networkHistory.rates,

        )
    }

}