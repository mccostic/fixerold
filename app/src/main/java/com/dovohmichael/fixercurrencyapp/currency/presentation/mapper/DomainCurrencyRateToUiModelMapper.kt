package com.dovohmichael.fixercurrencyapp.currency.presentation.mapper

import com.dovohmichael.fixercurrencyapp.currency.domain.model.CurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.CurrencyRateUIModel


object DomainCurrencyRateToUiModelMapper {

    fun map(currencyRate: CurrencyRate): CurrencyRateUIModel {
        return CurrencyRateUIModel(
            date = currencyRate.date,
            base = currencyRate.base,
            target = currencyRate.target,
            timestamp = currencyRate.timestamp,
            rate = currencyRate.rate,
            historical = currencyRate.historical
        )
    }

}