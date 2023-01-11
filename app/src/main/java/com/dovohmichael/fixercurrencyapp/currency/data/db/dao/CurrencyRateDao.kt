package com.dovohmichael.fixercurrencyapp.currency.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dovohmichael.fixercurrencyapp.core.data.db.BaseDao
import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrency
import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrencyRate

@Dao
abstract class CurrencyRateDao : BaseDao<DbCurrency>() {

    @Query(value = "SELECT * FROM currencies_rates where base_target=:baseTarget and date=:date")
    abstract suspend fun getRate(baseTarget:String,date:String): List<DbCurrencyRate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRate(currencyRate:  DbCurrencyRate)

}