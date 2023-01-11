package com.dovohmichael.fixercurrencyapp.currency.presentation.ui.converter

import android.content.Context

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.dovohmichael.fixercurrencyapp.LoadingAnimation
import com.dovohmichael.fixercurrencyapp.R
import com.dovohmichael.fixercurrencyapp.currency.presentation.CurrencyViewState
import com.dovohmichael.fixercurrencyapp.currency.presentation.viewmodel.CurrencyViewModel
import timber.log.Timber


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CurrencyBodyContent(
    context: Context,
    modifier: Modifier,
    navController: NavHostController,
    currencyViewModel: CurrencyViewModel = hiltViewModel()
) {

    var targetCurrencySymbol by rememberSaveable { mutableStateOf("GHS") }
    var baseCurrencySymbol by rememberSaveable { mutableStateOf("USD") }
    var baseAmount by rememberSaveable { mutableStateOf("1") }

    var swapCurrency by rememberSaveable { mutableStateOf(false) }


    val viewState by currencyViewModel.viewState.collectAsStateWithLifecycle()




    currencyViewModel.convert(base=baseCurrencySymbol, target = targetCurrencySymbol,baseAmount.toDouble())

    when (viewState) {
        is CurrencyViewState.Content -> {
            val data = viewState as CurrencyViewState.Content

            Column(modifier = Modifier.padding(24.dp, 0.dp)) {


                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    //base currency picker
                    CurrencyPicker(
                        modifier,
                        readOnly = false,
                        enabled = true,
                        defaultSymbol = baseCurrencySymbol,
                        data.currencyList,
                        onSymbolSelected = {
                            baseCurrencySymbol = it
                            currencyViewModel.onCurrencyChanged(
                                it,
                                baseAmount,
                                targetCurrencySymbol
                            )

                        }
                    )
                    //comparison arrow icon
                    IconButton(onClick = {
                        val temp= baseCurrencySymbol
                        baseCurrencySymbol = targetCurrencySymbol
                        targetCurrencySymbol = temp
                        swapCurrency = !swapCurrency



                        if(baseAmount.isNotEmpty() && targetCurrencySymbol.isNotEmpty() && baseCurrencySymbol.isNotEmpty()){
                            currencyViewModel.convert(
                                baseCurrencySymbol,
                                targetCurrencySymbol,
                                baseAmount.toDouble()
                            )
                        }
//                    currencyViewModel.setFromCurrency(targetCurrencySymbol)
//                    currencyViewModel.setToCurrency(baseCurrencySymbol)
//                    currencyViewModel.updateBase(newBaseAmount = String
//                        .format("%.3f", conversionRateState.result))
//                    currencyViewModel.updateConverted(baseAmount)

                    }) {
                        Icon(
                            imageVector = Icons.Filled.CompareArrows,
                            contentDescription = stringResource(R.string.compare_arrow_description),
                            modifier = Modifier.padding(8.dp),
                            tint = MaterialTheme.colors.onSecondary
                        )
                    }
                    CurrencyPicker(
                        modifier,
                        readOnly = false,
                        enabled = true,
                        defaultSymbol = targetCurrencySymbol,
                        data.currencyList,
                        onSymbolSelected = {
                            targetCurrencySymbol = it
                            currencyViewModel.onCurrencyChanged(
                                baseCurrencySymbol,
                                baseAmount,
                                it
                            )


                        }
                    )


                }
                Spacer(modifier = Modifier.height(32.dp))


                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CurrencyRateTextField(
                        baseCurrencySymbol,
                        modifier.weight(1.0f),
                        readOnly = false,
                        value = baseAmount,
                        enabled = true,
                        onBaseAmountChanged = { newBaseAmount ->
                            baseAmount = newBaseAmount
                            // currencyViewModel.updateBase(it)

                            if (newBaseAmount.isNotEmpty()) {
                                currencyViewModel.convert(
                                    baseCurrencySymbol,
                                    targetCurrencySymbol,
                                    newBaseAmount.toDouble()
                                )


                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp).weight(0.1f))
                    //base currency picker
                    CurrencyRateTextField(
                        targetCurrencySymbol,
                        modifier.weight(1.0f),
                        readOnly = false,
                        value = data.convertedAmount,

                        enabled = false,

                        onBaseAmountChanged = {

                        }
                    )

                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    elevation = null,
                    onClick = {

                    //val route = ScreenItems.History.route
                        //.replace(oldValue = "{target}", newValue = targetCurrencySymbol)
                        navController.currentBackStackEntry?.arguments?.apply {
                            putString("base",baseCurrencySymbol)
                            putString("target",targetCurrencySymbol)
                        }
                        navController.navigate("history?" +
                                "base=$baseCurrencySymbol," +
                                "target=$targetCurrencySymbol"
                        ){
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                    },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        contentColor = Color.White
                    )
                ) {
                    //convert text
                    Text(
                        text = stringResource(R.string.detail_text),
                        fontWeight = FontWeight.Bold, fontSize = 16.sp
                    )
                }

                //vertical spacing between convert button and promo text
                Spacer(modifier = Modifier.height(16.dp))


            }

        }
        is CurrencyViewState.Initial, is CurrencyViewState.Loading -> {
            LoadingAnimation()
        }
        else -> {

        }
    }

}

