package com.dovohmichael.fixercurrencyapp.currency.presentation

import com.dovohmichael.fixercurrencyapp.currency.domain.error.CurrencyError
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.CurrencyUIModel
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.HistoryUIModel

sealed class HistoryViewState {

    object Initial : HistoryViewState()

    object Loading : HistoryViewState()

    data class Content(
        val history: HistoryUIModel

    ) : HistoryViewState()

    data class Failed(val error: CurrencyError) : HistoryViewState()

}