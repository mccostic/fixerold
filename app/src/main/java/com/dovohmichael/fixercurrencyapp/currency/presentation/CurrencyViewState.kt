package com.dovohmichael.fixercurrencyapp.currency.presentation

import com.dovohmichael.fixercurrencyapp.currency.domain.error.CurrencyError
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.CurrencyUIModel

sealed class CurrencyViewState {

    object Initial : CurrencyViewState()

    object Loading : CurrencyViewState()

    data class Content(
        val convertedAmount:String="",
        val isRefreshing: Boolean = false,
        val currencyList: List<CurrencyUIModel> = listOf()

    ) : CurrencyViewState()

    data class Failed(val error: CurrencyError) : CurrencyViewState()

}