package com.dovohmichael.fixercurrencyapp.util

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.CurrencyRateUIModel
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.HistoryItem
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.HistoryUIModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

object Helpers {




    //get today's date
    fun todayDate(): String {
        val zoneId = ZoneId.of("Africa/Accra")
        return LocalDate.now(zoneId).toString()
    }

    fun lastThreeDaysDate(): String {
        val zoneId = ZoneId.of("Africa/Accra")
        return LocalDate.now(zoneId).minusDays(3).toString()
    }


    fun mapDataPoints(targetCurrency:String?,timeSeriesRates: HistoryUIModel?, target: String?): List<HistoryItem> {
        return if (timeSeriesRates?.rates != null) {
            val mapOfDataPoints = timeSeriesRates.rates.map { rate ->
                //remove non-word characters
                val dateValue = DecimalFormat("0000")
                    .format(rate.key.replace(Regex("\\W"), "")
                        .takeLast(6).toFloat()).toFloat()
                val rateValue = rate.value[target]?.toFloat() ?: 0f
                HistoryItem(
                    base=timeSeriesRates.base,
                    date = rate.key,
                    timeseries = timeSeriesRates.timeseries,
                    target = targetCurrency,
                    rate = rateValue
                )
            }
            mapOfDataPoints
        } else {
            listOf()
        }
    }

    fun mapDataPoints1(targetCurrency:String?,timeSeriesRates: HistoryUIModel?, target: String?): List<HistoryItem> {
        val response = mutableListOf<HistoryItem>()
        if (timeSeriesRates?.rates != null) {
           timeSeriesRates.rates.map { rate ->
                //remove non-word characters
                for (k in rate.value) {
                    if(k.key!=target) {
                        response.add(
                            HistoryItem(
                                base = timeSeriesRates.base,
                                date = rate.key,
                                timeseries = timeSeriesRates.timeseries,
                                target = k.key,
                                rate = k.value.toFloat()
                            )
                        )
                    }
                }

            }
        }
        return response
    }


    fun calculateMonth(value: Int): String? {
        val map = mapOf<Int, String>(
            1 to "Jan",
            2 to "Feb",
            3 to "Mar",
            4 to "Apr",
            5 to "May",
            6 to "Jun",
            7 to "Jul",
            8 to "Aug",
            9 to "Sep",
            10 to "Oct",
            11 to "Nov",
            12 to "Dec"
        )
        return map[value.toInt()]
    }

}


