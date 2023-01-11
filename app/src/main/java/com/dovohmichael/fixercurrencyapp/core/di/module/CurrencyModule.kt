package com.dovohmichael.fixercurrencyapp.core.di.module

import com.dovohmichael.fixercurrencyapp.core.data.db.CurrencyDatabase
import com.dovohmichael.fixercurrencyapp.currency.data.CurrencyRepositoryImpl
import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.data.mapper.*
import com.dovohmichael.fixercurrencyapp.currency.data.network.CurrencyConverterApi
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkCurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.domain.CurrencyRepository
import com.dovohmichael.fixercurrencyapp.currency.domain.model.CurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.domain.usecase.ConvertCurrencyUseCase
import com.dovohmichael.fixercurrencyapp.currency.domain.usecase.GetCurrencyRateUseCase
import com.dovohmichael.fixercurrencyapp.currency.domain.usecase.GetCurrencyUseCase
import com.dovohmichael.fixercurrencyapp.currency.domain.usecase.GetHistoryUseCase
import com.dovohmichael.fixercurrencyapp.currency.presentation.mapper.DomainConvertedCurrencyToUiModelMapper
import com.dovohmichael.fixercurrencyapp.currency.presentation.mapper.DomainCurrencyRateToUiModelMapper
import com.dovohmichael.fixercurrencyapp.currency.presentation.mapper.DomainCurrencyToUiModelMapper
import com.dovohmichael.fixercurrencyapp.currency.presentation.mapper.DomainHistoryToUiModelMapper

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [CoreModule::class])
abstract class CurrencyModule {

    companion object {

        @Singleton
        @Provides
        fun provideCurrencyRepository(
            retrofit: Retrofit,
            database: CurrencyDatabase
        ): CurrencyRepository {
            return CurrencyRepositoryImpl(
                currencyConverterApi = retrofit.create(CurrencyConverterApi::class.java),
                currencyDao = database.currencyDao(),
                currencyRateDao=database.currencyRateDao(),
                mapNetworkCurrencyToDb = NetworkCurrencyToDbMapper::map,
                mapDbCurrencyToDomain= DbCurrencyToDomainMapper::map,
                mapNetworkConvertedToDomain = NetworkConvertedCurrencyToDomainMapper::map,
               mapNetworkCurrencyRateToDb= NetworkCurrencyRateToDbMapper::map,
             mapDbCurrencyRateToDomain=  DbCurrencyRateToDomainMapper::map,
             mapNetworkCurrencyRateToDomain= NetworkCurrencyRateToDomainMapper::map,
                mapNetworkHistoryToDomain = NetworkHistoryToDomainMapper::map,
            )
        }

        @Singleton
        @Provides
        fun provideMapDomainCurrencyToUIModel(): DomainCurrencyToUiModelMapper = DomainCurrencyToUiModelMapper



        @Singleton
        @Provides
        fun provideMapDomainCurrencyRateToUIModel(): DomainCurrencyRateToUiModelMapper = DomainCurrencyRateToUiModelMapper



        @Singleton
        @Provides
        fun provideMapDomainConvertedCurrencyToUIModel(): DomainConvertedCurrencyToUiModelMapper = DomainConvertedCurrencyToUiModelMapper


        @Singleton
        @Provides
        fun provideMapDomainHistoryToUIModel(): DomainHistoryToUiModelMapper = DomainHistoryToUiModelMapper


        @Singleton
        @Provides
        fun provideGetCurrenciesUseCase(currencyRepository: CurrencyRepository)=GetCurrencyUseCase(currencyRepository)


        @Singleton
        @Provides
        fun provideConvertCurrencyUseCase(currencyRepository: CurrencyRepository)=
            ConvertCurrencyUseCase(currencyRepository)

        @Singleton
        @Provides
        fun provideGetHistoryUseCase(currencyRepository: CurrencyRepository)= GetHistoryUseCase(currencyRepository)


        @Singleton
        @Provides
        fun provideCurrencyRateUseCase(currencyRepository: CurrencyRepository)=
            GetCurrencyRateUseCase(currencyRepository)


    }

}