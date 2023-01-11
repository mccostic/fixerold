package com.dovohmichael.fixercurrencyapp.currency.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CurrencyConverterApi {
    @GET(value = "/fixer/symbols")
    suspend fun fetchCurrencyList(): Response<NetworkCurrencies>

    @GET(value = "/fixer/timeseries")
    suspend fun fetchHistory(@Query("start_date") start_date:String,
                             @Query("end_date") end_date:String,
                             @Query("base") base:String,
                             @Query("symbols") symbols:String): Response<NetworkHistory>

    @GET(value = "/fixer/convert")
    suspend fun convertCurrency(@Query("from") from:String, @Query("to") to:String, @Query("amount") amount:Double): Response<NetworkConvertedCurrency>

//https://api.apilayer.com/fixer/2022-12-31?symbols=GHS&base=USD"
// "https://api.apilayer.com/fixer/2022-12-31?symbols=GHS%2CGBP&base=USD"
    @GET(value = "/fixer/{date}")
    suspend fun getRate(@Path("date") date:String,
                        @Query("symbols") symbols:String,
                        @Query("base") base:String): Response<NetworkCurrencyRate>
}