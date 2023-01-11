package com.dovohmichael.fixercurrencyapp.currency.domain.error

sealed class CurrencyError {


    object NetworkError : CurrencyError()

    object UnknownError : CurrencyError()
}
