package com.dovohmichael.fixercurrencyapp.currency.data

import com.dovohmichael.fixercurrencyapp.core.domain.model.Result
import com.dovohmichael.fixercurrencyapp.currency.data.db.dao.CurrencyDao
import com.dovohmichael.fixercurrencyapp.currency.data.db.dao.CurrencyRateDao
import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrency
import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.data.network.CurrencyConverterApi
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkConvertedCurrency
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkCurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkHistory
import com.dovohmichael.fixercurrencyapp.currency.domain.CurrencyRepository
import com.dovohmichael.fixercurrencyapp.currency.domain.error.CurrencyError
import com.dovohmichael.fixercurrencyapp.currency.domain.model.ConvertedCurrency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.Currency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.CurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.domain.model.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import timber.log.Timber
import java.net.HttpURLConnection

class CurrencyRepositoryImpl constructor(
    private val currencyConverterApi: CurrencyConverterApi,
    private val currencyDao: CurrencyDao,
    private val currencyRateDao: CurrencyRateDao,
    private val mapNetworkCurrencyToDb: (Map.Entry<String,String>) -> DbCurrency,
    private val mapDbCurrencyToDomain: (DbCurrency) -> Currency,
    private val mapNetworkConvertedToDomain: (NetworkConvertedCurrency) -> ConvertedCurrency,
    private val mapNetworkCurrencyRateToDb:(NetworkCurrencyRate)->DbCurrencyRate,
    private val mapDbCurrencyRateToDomain: (DbCurrencyRate) -> CurrencyRate,
    private val mapNetworkCurrencyRateToDomain: (NetworkCurrencyRate) -> CurrencyRate,
    private val mapNetworkHistoryToDomain: (NetworkHistory) -> History,
) : CurrencyRepository {


    override suspend fun getCurrencies(): Result<CurrencyError, List<Currency>> = withContext(Dispatchers.IO) {
        try {
            var dbCurrencyList = currencyDao.getCurrencies().map { mapDbCurrencyToDomain.invoke(it) }

            if (dbCurrencyList.isEmpty()) {


                val currencyListResponse = currencyConverterApi.fetchCurrencyList()
                if (currencyListResponse.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                    return@withContext Result.Fail(CurrencyError.NetworkError)
                }

                //val currentWeather = currencyListResponse.body()!!
                val currencyList = currencyListResponse.body()!!.list

                val res = currencyList.map { mapNetworkCurrencyToDb.invoke(it) }
                currencyDao.addCurrency(res)
                dbCurrencyList = res.map { mapDbCurrencyToDomain.invoke(it) }
            }



            Result.Success(data = dbCurrencyList)
        }
        catch (exception:Exception){
            Timber.e(exception)
            if (exception is IOException) {
                return@withContext Result.Fail(CurrencyError.NetworkError)
            }
            else Result.Fail(CurrencyError.UnknownError)

        }
    }

    override suspend fun convert(
        from: String,
        to: String,
        amount: Double
    ): Result<CurrencyError, ConvertedCurrency> = withContext(Dispatchers.IO){
        try {
            val convertCurrencyResponse = currencyConverterApi.convertCurrency(from=from,to=to,amount=amount)
            if (convertCurrencyResponse.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                return@withContext Result.Fail(CurrencyError.NetworkError)
            }
            val body = convertCurrencyResponse.body()!!
            val converted = mapNetworkConvertedToDomain.invoke(body)
            Result.Success(converted)
        }
        catch (exception:Exception){
            Timber.e(exception)
            if (exception is IOException) {
                return@withContext Result.Fail(CurrencyError.NetworkError)
            }
            Result.Fail(CurrencyError.UnknownError)
        }
    }

    override suspend fun getRate(
        base: String,
        target: String,
        date: String
    ): Result<CurrencyError, CurrencyRate> = withContext(Dispatchers.IO){


        try {
            val dbCurrencyRate = currencyRateDao.getRate(baseTarget ="$base:$target:$date",date=date)

            if (dbCurrencyRate.isEmpty()) {


                val currencyRateResponse = currencyConverterApi.getRate(date=date, symbols = target,base=base)
                if (currencyRateResponse.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                    return@withContext Result.Fail(CurrencyError.NetworkError)
                }


                val currencyRate = currencyRateResponse.body()!!


                currencyRateDao.addRate(mapNetworkCurrencyRateToDb.invoke(currencyRate))


                Result.Success(data=mapNetworkCurrencyRateToDomain.invoke(currencyRate))
            }
            else

            Result.Success(data =mapDbCurrencyRateToDomain.invoke(dbCurrencyRate.first()) )
        }
        catch (exception:Exception){
            Timber.e(exception)
            if (exception is IOException) {
                return@withContext Result.Fail(CurrencyError.NetworkError)
            }
            Result.Fail(CurrencyError.UnknownError)

        }
    }


    override suspend fun getHistory(
        base: String,
        target: String,
        startDate:String,
        endDate: String,

    ): Result<CurrencyError, History> = withContext(Dispatchers.IO){


        try {


            val currencyRateResponse = currencyConverterApi.fetchHistory(symbols = target,base=base, start_date = startDate, end_date = endDate)
            if (currencyRateResponse.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                return@withContext Result.Fail(CurrencyError.NetworkError)
            }


            val currencyRate = currencyRateResponse.body()!!

            Result.Success(data=mapNetworkHistoryToDomain.invoke(currencyRate))

        }
        catch (exception:Exception){
            Timber.e(exception)
            if (exception is IOException) {
                return@withContext Result.Fail(CurrencyError.NetworkError)
            }
            Result.Fail(CurrencyError.UnknownError)

        }
    }


}