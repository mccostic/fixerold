package com.dovohmichael.fixercurrencyapp.currency.presentation.ui.converter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes



sealed class ScreenItems(
    var route: String,
) {
    object Converter :
        ScreenItems(
            "converter"
        )

    object History :
        ScreenItems(
            "history?base={base},target={target}"
        )

}