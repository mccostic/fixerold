package com.dovohmichael.fixercurrencyapp.currency.presentation.ui.converter

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


@Composable
fun HomeNavigationGraph(navController: NavHostController,context:Context) {
    NavHost(
        navController = navController,
        startDestination = ScreenItems.Converter.route) {

        composable(ScreenItems.Converter.route) {
            CurrencyRootContent(context=context,navHostController=navController)
        }

        composable(ScreenItems.History.route,arguments = listOf(
            navArgument("base") {
                type = NavType.StringType
            },
            navArgument("target") {
                type = NavType.StringType
            }
        )) { backStackEntry ->
        HistoryRootContent(target = backStackEntry.arguments?.getString("target"), base=backStackEntry.arguments?.getString("base"), navHostController = navController)
        }
    }
}