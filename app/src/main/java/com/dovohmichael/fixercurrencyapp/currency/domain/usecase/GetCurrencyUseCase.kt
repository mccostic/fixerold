package com.dovohmichael.fixercurrencyapp.currency.domain.usecase

import com.dovohmichael.fixercurrencyapp.currency.domain.CurrencyRepository


class GetCurrencyUseCase(private val currencyRepository: CurrencyRepository) {
    suspend operator fun invoke(

    ) = currencyRepository.getCurrencies()
}