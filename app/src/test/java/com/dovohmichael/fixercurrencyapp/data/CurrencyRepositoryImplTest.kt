package com.dovohmichael.fixercurrencyapp.data


import com.dovohmichael.fixercurrencyapp.currency.data.CurrencyRepositoryImpl
import com.dovohmichael.fixercurrencyapp.currency.data.db.dao.CurrencyDao
import com.dovohmichael.fixercurrencyapp.currency.data.db.dao.CurrencyRateDao
import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrency
import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.data.network.CurrencyConverterApi
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkConvertedCurrency
import com.dovohmichael.fixercurrencyapp.core.domain.model.Result
import com.dovohmichael.fixercurrencyapp.currency.data.network.NetworkCurrencyRate
import com.dovohmichael.fixercurrencyapp.currency.domain.CurrencyRepository
import com.dovohmichael.fixercurrencyapp.currency.domain.error.CurrencyError
import com.dovohmichael.fixercurrencyapp.currency.domain.model.ConvertedCurrency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.Currency
import com.dovohmichael.fixercurrencyapp.currency.domain.model.CurrencyRate
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.threeten.bp.ZonedDateTime
import retrofit2.Response
import timber.log.Timber

class CurrencyRepositoryImplTest {

    private val mockCurrencyApi = mockk<CurrencyConverterApi>()
    private val mockCurrencyRateDao = mockk<CurrencyRateDao>(relaxUnitFun = true)
    private val mockCurrencyDao = mockk<CurrencyDao>(relaxUnitFun = true)

    private val mockMapNetworkCurrencyRateToDb = mockk<(NetworkCurrencyRate) -> DbCurrencyRate>()
    private val mockMapDbCurrencyToDomain = mockk<(DbCurrency) -> Currency>()
    private val mockMapNetworkCurrencyToDb = mockk<(Map.Entry<String, String>) -> DbCurrency>()
    private val mockMapNetworkConvertedToDomain =
        mockk<(NetworkConvertedCurrency) -> ConvertedCurrency>()
    private val mockMapNetworkCurrencyRateToDomain = mockk<(NetworkCurrencyRate) -> CurrencyRate>()
    private val mockMapDbCurrencyRateToDomain = mockk<(DbCurrencyRate) -> CurrencyRate>()

    private lateinit var repository: CurrencyRepository


    @Before
    fun setUp() {
        repository = CurrencyRepositoryImpl(
            currencyConverterApi = mockCurrencyApi,
            currencyDao = mockCurrencyDao,
            currencyRateDao = mockCurrencyRateDao,
            mapNetworkCurrencyToDb = mockMapNetworkCurrencyToDb,
            mapDbCurrencyToDomain = mockMapDbCurrencyToDomain,
            mapNetworkConvertedToDomain = mockMapNetworkConvertedToDomain,
            mapNetworkCurrencyRateToDb = mockMapNetworkCurrencyRateToDb,
            mapDbCurrencyRateToDomain = mockMapDbCurrencyRateToDomain,
            mapNetworkCurrencyRateToDomain = mockMapNetworkCurrencyRateToDomain
        )
    }


    @Test
    fun `getCurrencies should return network error if a network error occurs when fetching Currencies`() =
        runBlocking {
            coEvery { mockCurrencyApi.fetchCurrencyList() }

                .throws(IOException())

           coEvery {  mockCurrencyDao.getCurrencies() }.returns(listOf())


            val result = repository.getCurrencies()

           // Timber.tag("fetch currency").d(result.toString())
            Truth.assertThat(result).isInstanceOf((Result.Fail::class.java))
            assertEquals(CurrencyError.NetworkError, (result as Result.Fail).error)
            coVerify { mockCurrencyApi.fetchCurrencyList() }
        }



    @Test
    fun `getCurrencies should call network api if Currency List is empty in database`() =
        runBlocking {
            coEvery { mockCurrencyDao.getCurrencies() }.returns(listOf())

            repository.getCurrencies()

            coVerify { mockCurrencyDao.getCurrencies() }
            coVerify { mockCurrencyApi.fetchCurrencyList() }
        }

    @Test
    fun `getRates should call network api if rate does not exist in database`() =
        runBlocking {
            coEvery { mockCurrencyRateDao.getRate(any(),any()) }.returns(listOf())

            repository.getRate("USD","GHS","2023-01-07")

            coVerify { mockCurrencyRateDao.getRate(any(),any()) }
            coVerify { mockCurrencyApi.getRate(any(),any(),any()) }
        }



    /*@Test
    fun `getWeatherForCity should call network api if location name does not exist in database`() =
        runBlocking {
            coEvery { mockLocationDao.hasLocation(any()) }.returns(false)

            repository.getWeatherForCity(city = "")

            coVerify { mockLocationDao.hasLocation(any()) }
            coVerify { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
        }

    @Test
    fun `getWeatherForCity should call network api if location data has expired`() = runBlocking {
        val dbLocation = LocationTestFactory.makeDbLocation(
            lastUpdatedAt = ZonedDateTime.now().minusMinutes(6).toInstant().toEpochMilli()
        )

        coEvery { mockLocationDao.hasLocation(any()) }.returns(true)
        coEvery { mockLocationDao.findByName(any()) }
            .returns(dbLocation)

        repository.getWeatherForCity(city = "")

        coVerify { mockLocationDao.hasLocation(any()) }
        coVerify { mockLocationDao.findByName(any()) }
        coVerify { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
    }

    @Test
    fun `getWeatherForCity should return network error if a network error occurs when fetching current weather`() =
        runBlocking {
            coEvery { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
                .throws(IOException())

            val result = repository.getWeatherForCity(city = "", refresh = true)

            assertThat(result, instanceOf(Result.Fail::class.java))
            assertEquals(GetWeatherError.NetworkError, (result as Result.Fail).error)
            coVerify { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
        }

    @Test
    fun `getWeatherForCity should return unknown error if an unexpected error occurs when fetching current weather`() =
        runBlocking {
            coEvery { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
                .throws(Exception())

            val result = repository.getWeatherForCity(city = "", refresh = true)

            assertThat(result, instanceOf(Result.Fail::class.java))
            assertEquals(GetWeatherError.UnknownError, (result as Result.Fail).error)

        }


    @Test
    fun `getWeatherForCity should return location not found error if location does not exist when fetching current weather`() =
        runBlocking {
            coEvery { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
                .returns(Response.error(404, "{}".toResponseBody()))

            val result = repository.getWeatherForCity(city = "", refresh = true)

            assertThat(result, instanceOf(Result.Fail::class.java))
            assertEquals(GetWeatherError.LocationNotFoundError, (result as Result.Fail).error)
            coVerify { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
        }


    @Test
    fun `getWeatherForCity should return network error if a network error occurs when fetching forecast weather`() =
        runBlocking {

            coEvery { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
                .returns(Response.success(WeatherTestFactory.makeNetworkCurrentWeather()))
            coEvery { mockWeatherApi.fetchForecastWeatherForCity(any()) }
                .throws(IOException())

            val result = repository.getWeatherForCity(city = "", refresh = true)

            assertThat(result, instanceOf(Result.Fail::class.java))
            assertEquals(GetWeatherError.NetworkError, (result as Result.Fail).error)
            coVerify { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
            coVerify { mockWeatherApi.fetchForecastWeatherForCity(any()) }
        }

    @Test
    fun `getWeatherForCity should return unknown error if an unexpected error occurs when fetching forecast weather`() =
        runBlocking {
            coEvery { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
                .returns(Response.success(WeatherTestFactory.makeNetworkCurrentWeather()))
            coEvery { mockWeatherApi.fetchForecastWeatherForCity(any()) }
                .throws(Exception())

            val result = repository.getWeatherForCity(city = "", refresh = true)

            assertThat(result, instanceOf(Result.Fail::class.java))
            assertEquals(GetWeatherError.UnknownError, (result as Result.Fail).error)
            coVerify { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
            coVerify { mockWeatherApi.fetchForecastWeatherForCity(any()) }
        }

    @Test
    fun `getWeatherForCity should return location not found error if an unexpected error occurs when fetching forecast weather`() =
        runBlocking {

            coEvery { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
                .returns(Response.success(WeatherTestFactory.makeNetworkCurrentWeather()))
            coEvery { mockWeatherApi.fetchForecastWeatherForCity(any()) }
                .returns(Response.error(404, "{}".toResponseBody()))

            val result = repository.getWeatherForCity(city = "", refresh = true)

            assertThat(result, instanceOf(Result.Fail::class.java))
            assertEquals(GetWeatherError.LocationNotFoundError, (result as Result.Fail).error)
            coVerify { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
            coVerify { mockWeatherApi.fetchForecastWeatherForCity(any()) }

        }

    @Test
    fun `getWeatherForCity should return weather if weather data already exists in database and has not expired`() =
        runBlocking {
            val weather = WeatherTestFactory.makeWeather()
            coEvery { mockLocationDao.hasLocation(any()) }.returns(true)
            coEvery { mockLocationDao.findByName(any()) }
                .returns(
                    LocationTestFactory.makeDbLocation(
                        lastUpdatedAt = ZonedDateTime.now().toInstant().toEpochMilli()
                    )
                )
            coEvery { mockCurrentWeatherDao.findForLocation(any()) }
                .returns(WeatherTestFactory.makeDbCurrentWeather())
            coEvery { mockForecastWeatherDao.findForLocation(any()) }
                .returns(listOf(WeatherTestFactory.makeDbForecastWeather()))
            coEvery { mockMapDbWeatherToDomain(any(), any(), any()) }
                .returns(weather)

            val result = repository.getWeatherForCity(city = "")

            assertThat(result, instanceOf(Result.Ok::class.java))
            assertEquals(weather, (result as Result.Ok).data)
            coVerify { mockLocationDao.findByName(any()) }
            coVerify { mockCurrentWeatherDao.findForLocation(any()) }
            coVerify { mockForecastWeatherDao.findForLocation(any()) }
            verify { mockMapDbWeatherToDomain(any(), any(), any()) }

        }
*/
    /*@Test
    fun `getWeatherForCity should return weather after calling network apis`() = runBlocking {
        val dbCurrentWeather = WeatherTestFactory.makeDbCurrentWeather()
        val dbForecastWeather = WeatherTestFactory.makeDbForecastWeather()
        val weather = WeatherTestFactory.makeWeather()


        coEvery { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
            .returns(Response.success(WeatherTestFactory.makeNetworkCurrentWeather()))
        coEvery { mockWeatherApi.fetchForecastWeatherForCity(any()) }
            .returns(
                Response.success(
                    FetchForecastWeatherResponse(
                        list = listOf(
                            WeatherTestFactory.makeNetworkForecastWeather(),
                            WeatherTestFactory.makeNetworkForecastWeather()
                        )
                    )
                )
            )
        coEvery { mockLocationDao.findByName(any()) }.returns(LocationTestFactory.makeDbLocation())
        coEvery { mockCurrentWeatherDao.findForLocation(any()) }
            .returns(dbCurrentWeather)
        coEvery { mockForecastWeatherDao.findForLocation(any()) }
            .returns(listOf(dbForecastWeather))
        coEvery { mockMapNetworkCurrentWeatherToDb(any(), any()) }
            .returns(dbCurrentWeather)
        coEvery { mockMapNetworkForecastWeatherToDb(any(), any()) }
            .returns(dbForecastWeather)
        coEvery { mockMapDbWeatherToDomain(any(), any(), any()) }
            .returns(weather)


        val result = repository.getWeatherForCity(city = "", refresh = true)


        assertThat(result, instanceOf(Result.Ok::class.java))
        assertEquals(weather, (result as Result.Ok).data)
        coVerify { mockWeatherApi.fetchCurrentWeatherForCity(any()) }
        coVerify { mockWeatherApi.fetchForecastWeatherForCity(any()) }
        coVerify { mockLocationDao.deleteByName(any()) }
        coVerify { mockLocationDao.insertOrUpdate(any()) }
        coVerify { mockCurrentWeatherDao.insertOrUpdate(any()) }
        coVerify { mockForecastWeatherDao.insertOrUpdate(*anyVararg()) }
        coVerify { mockLocationDao.findByName(any()) }
        coVerify { mockCurrentWeatherDao.findForLocation(any()) }
        coVerify { mockForecastWeatherDao.findForLocation(any()) }
        verify { mockMapNetworkCurrentWeatherToDb(any(), any()) }
        verify { mockMapNetworkForecastWeatherToDb(any(), any()) }
        verify { mockMapDbWeatherToDomain(any(), any(), any()) }

    }*/
}















