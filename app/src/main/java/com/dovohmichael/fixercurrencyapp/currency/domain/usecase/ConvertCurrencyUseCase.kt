package com.dovohmichael.fixercurrencyapp.currency.domain.usecase

import com.dovohmichael.fixercurrencyapp.currency.domain.CurrencyRepository


class ConvertCurrencyUseCase(private val currencyRepository: CurrencyRepository) {
    suspend operator fun invoke(
        from :String, to:String, amount:Double
    ) = currencyRepository.convert(from = from, to=to, amount=amount)
}