package com.dovohmichael.fixercurrencyapp.currency.data.mapper

import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrency
import java.util.*

object NetworkCurrencyToDbMapper {

    fun map(networkCurrency: Map.Entry<String,String>): DbCurrency {
        return DbCurrency(
            iso = networkCurrency.key,
            name = networkCurrency.value)
    }

}