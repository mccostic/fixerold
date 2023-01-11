package com.dovohmichael.fixercurrencyapp.currency.presentation.ui.converter

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dovohmichael.fixercurrencyapp.R

@Composable
fun CurrencyRootContent(
    context: Context,
    navHostController: NavHostController,
) {
    //use default theme
    Scaffold(
    ) { innerPadding -> //body content of main activity
        CurrencyBodyContent(
            context,
            Modifier.padding(innerPadding),
            navController= navHostController,
        )
    }
}


@Composable
fun HistoryRootContent(
    navHostController: NavHostController,
    base:String?,
    target:String?
) {
    //use default theme
    Scaffold(
    ) { innerPadding ->{

    } //body content of main activity
        HistoryBodyContent(
            Modifier.padding(innerPadding),
            navController= navHostController,
            baseCurrency = base,
            targetCurrency = target
        )
    }
}

@Composable
fun HomeScreen(context: Context) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { //top app bar
            TopAppBar(
                modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                elevation = 0.dp,
                contentColor = MaterialTheme.colors.primaryVariant,
                backgroundColor = MaterialTheme.colors.background,
                title = {Text(stringResource(R.string.app_name),   fontWeight = FontWeight.Bold, fontSize = 16.sp)},
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeNavigationGraph(navController = navController,context=context)
        }
    }
}
