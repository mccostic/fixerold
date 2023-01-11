package com.dovohmichael.fixercurrencyapp.currency.domain.usecase

import com.dovohmichael.fixercurrencyapp.currency.domain.CurrencyRepository


class GetCurrencyRateUseCase(private val currencyRepository: CurrencyRepository) {
    suspend operator fun invoke(
        base :String, target:String, date:String
    ) = currencyRepository.getRate(base = base, target =target, date = date)
}