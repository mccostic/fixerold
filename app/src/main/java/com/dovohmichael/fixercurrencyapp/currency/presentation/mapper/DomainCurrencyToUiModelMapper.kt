package com.dovohmichael.fixercurrencyapp.currency.presentation.mapper

import com.dovohmichael.fixercurrencyapp.currency.domain.model.Currency
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.CurrencyUIModel


object DomainCurrencyToUiModelMapper {

    fun map(currency: Currency): CurrencyUIModel {
        return CurrencyUIModel(
           iso = currency.isoAlpha3,
            name = currency.name
        )
    }

}