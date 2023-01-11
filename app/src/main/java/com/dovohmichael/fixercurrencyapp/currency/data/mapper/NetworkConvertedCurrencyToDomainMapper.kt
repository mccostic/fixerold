package com.dovohmichael.fixercurrencyapp.currency.data.mapper

import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrency
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkConvertedCurrency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.ConvertedCurrency
import java.util.*

object NetworkConvertedCurrencyToDomainMapper {

    fun map(networkConvertedCurrency: NetworkConvertedCurrency): ConvertedCurrency {
        return ConvertedCurrency(
            baseAmount = networkConvertedCurrency.query.amount,
            convertedAmount = networkConvertedCurrency.result,
            baseCurrency = networkConvertedCurrency.query.from,
            targetCurrency = networkConvertedCurrency.query.to
        )
    }

}