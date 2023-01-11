package com.dovohmichael.fixercurrencyapp.currency.presentation.ui.converter

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dovohmichael.fixercurrencyapp.currency.presentation.model.HistoryItem

import com.dovohmichael.fixercurrencyapp.currency.presentation.model.HistoryUIModel


@Composable
fun HistoryItem(
    history: HistoryItem,
    backgroundColor: Color,
    textColor: Color,
    onEditClick: () -> Unit,
) {
    Card(
        backgroundColor = backgroundColor,
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                Text(
                    text = String.format("%.3f",history.rate),
                    color = MaterialTheme.colors.secondaryVariant,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${history.base} | ${history.target}",
                    color = textColor
                )
                Text(
                    text = "Date: ${history.date}",
                    color = textColor
                )
            }

        }
    }
}

