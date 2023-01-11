package com.dovohmichael.fixercurrencyapp.currency.presentation.mapper

import com.dovohmichael.fixercurrencyapp.currency.domain.model.ConvertedCurrency
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.ConvertedCurrencyUIModel


object DomainConvertedCurrencyToUiModelMapper {

    fun map(convertedCurrency: ConvertedCurrency): ConvertedCurrencyUIModel {
        return ConvertedCurrencyUIModel(
            baseAmount = convertedCurrency.baseAmount,
            convertedAmount = convertedCurrency.convertedAmount,
            baseCurrency = convertedCurrency.baseCurrency,
            targetCurrency = convertedCurrency.targetCurrency
        )
    }

}