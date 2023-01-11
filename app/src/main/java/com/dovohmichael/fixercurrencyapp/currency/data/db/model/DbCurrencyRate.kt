package com.dovohmichael.fixercurrencyapp.currency.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "currencies_rates"
)
data class DbCurrencyRate(
    @PrimaryKey
    @ColumnInfo(name = "base_target")
    var baseTarget:String,
    var base:String,
    var date:String,
    var historical:Boolean,
    var timestamp:Long,
    var target:String,
    var rate:Double)

//{
//    "base": "USD",
//    "date": "2022-12-31",
//    "historical": true,
//    "rates": {
//    "GHS": 10.15039
//},
//    "success": true,
//    "timestamp": 1672482603
//}