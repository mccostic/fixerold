package com.dovohmichael.fixercurrencyapp.currency.data.mapper

import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.Currency


object DbCurrencyToDomainMapper {

    fun map(dbCurrency: DbCurrency): Currency {
        return Currency(
           isoAlpha3 = dbCurrency.iso,
            name = dbCurrency.name
        )
    }

}