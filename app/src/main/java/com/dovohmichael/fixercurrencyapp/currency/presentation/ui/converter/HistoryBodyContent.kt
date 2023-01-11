package com.dovohmichael.fixercurrencyapp.currency.presentation.ui.converter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.dovohmichael.fixercurrencyapp.currency.presentation.HistoryViewState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.dovohmichael.fixercurrencyapp.LoadingAnimation
import com.dovohmichael.fixercurrencyapp.currency.domain.error.CurrencyError
import com.dovohmichael.fixercurrencyapp.currency.presentation.viewmodel.HistoryViewModel
import com.dovohmichael.fixercurrencyapp.util.Helpers
import timber.log.Timber

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HistoryBodyContent(
    modifier: Modifier,
    baseCurrency: String?,
    targetCurrency: String?,
    navController: NavHostController,
    historyViewModel: HistoryViewModel = hiltViewModel()
) {

    val base by rememberSaveable {
        mutableStateOf(baseCurrency)
    }
    val target by rememberSaveable {
        mutableStateOf(targetCurrency)
    }




    val viewState by historyViewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = base, key2 = target){
        historyViewModel.getHistory(base= base!!, target= target!!)
    }







    when (viewState) {
        is HistoryViewState.Content -> {
            val historyList = Helpers.mapDataPoints(targetCurrency= target,(viewState as HistoryViewState.Content).history,target=target)
            val historiez = Helpers.mapDataPoints1(targetCurrency=target,(viewState as HistoryViewState.Content).history,target=target)

            Timber.tag("HISO").d(historiez.toString())
            Box(contentAlignment = Alignment.Center) {
                Row {

                    LazyColumn(modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()) {
                        items(historyList) { history ->
                            HistoryItem(
                                history = history,
                                onEditClick = {

                                },
                                textColor = Color.White,
                                backgroundColor = MaterialTheme.colors.primary)
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.1f))

                    LazyColumn(modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                    ) {
                        items(historiez) { history ->
                            HistoryItem(
                                history = history,
                                onEditClick = {

                                },
                                textColor = Color.Black,
                                backgroundColor = MaterialTheme.colors.secondary)
                        }
                    }
                }

            }

        }
        is HistoryViewState.Loading -> {
            LoadingAnimation()
        }
        is HistoryViewState.Failed -> {
            val error = (viewState as HistoryViewState.Failed)

            if(error.error is CurrencyError.NetworkError){
                Text(text = "Network Error occurred")
            }
            else
            {
                Text(text = "Unknown Error occurred")
            }
        }
        else -> {}
    }

}

