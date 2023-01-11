package com.dovohmichael.fixercurrencyapp.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dovohmichael.fixercurrencyapp.currency.data.db.dao.CurrencyDao
import com.dovohmichael.fixercurrencyapp.currency.data.db.dao.CurrencyRateDao
import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrency
import com.dovohmichael.fixercurrencyapp.currency.data.db.model.DbCurrencyRate


@Database(
    entities = [DbCurrency::class,DbCurrencyRate::class],
    exportSchema = false,
    version = 1
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun currencyRateDao():CurrencyRateDao


    companion object {
        private var INSTANCE: CurrencyDatabase? = null

        fun getInstance(context: Context): CurrencyDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    CurrencyDatabase::class.java,
                    "weatheroo.db"
                ).build()
            }

            return INSTANCE as CurrencyDatabase
        }
    }

}