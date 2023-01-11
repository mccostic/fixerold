package com.dovohmichael.fixercurrencyapp.currency.presentation.mapper

import com.dovohmichael.fixercurrencyapp.currency.domain.model.Currency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.History
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.CurrencyUIModel
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.HistoryUIModel


object DomainHistoryToUiModelMapper {

    fun map(history: History): HistoryUIModel {
        return HistoryUIModel(
            startDate = history.startDate,
            base = history.base,
            target = "networkHistory",
            timeseries = history.timeseries,
            rates = history.rates,
        )
    }

}