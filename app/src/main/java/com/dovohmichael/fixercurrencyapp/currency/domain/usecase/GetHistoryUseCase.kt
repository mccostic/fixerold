package com.dovohmichael.fixercurrencyapp.currency.domain.usecase

import com.dovohmichael.fixercurrencyapp.currency.domain.CurrencyRepository

class GetHistoryUseCase(private val currencyRepository: CurrencyRepository) {
    suspend operator fun invoke(
        base :String, target:String, startDate:String,endDate:String
    ) = currencyRepository.getHistory(base = base, target =target, startDate = startDate,endDate=endDate)
}