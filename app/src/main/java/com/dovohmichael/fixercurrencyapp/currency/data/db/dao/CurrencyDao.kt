package com.dovohmichael.fixercurrencyapp.currency.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dovohmichael.fixercurrencyapp.core.data.db.BaseDao
import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrency

@Dao
abstract class CurrencyDao : BaseDao<DbCurrency>() {

    @Query(value = "SELECT * FROM currencies")
    abstract suspend fun getCurrencies(): List<DbCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addCurrency(currencies:  List<DbCurrency>)

}