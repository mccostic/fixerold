package com.dovohmichael.fixercurrencyapp.currency.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "currencies",
    indices = [Index("name")]
)
data class DbCurrency(

    @PrimaryKey
    @ColumnInfo(name = "iso")
    val iso: String,

    @ColumnInfo(name = "name")
    val name: String
)