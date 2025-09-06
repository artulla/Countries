package com.graphql.country_api.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.graphql.country_api.presentation.ui.country.screen.CountriesScreen
import com.graphql.country_api.presentation.ui.country.screen.CountryDetailScreen
import com.graphql.country_api.presentation.ui.country.viewmodel.CountriesViewModel
import com.graphql.country_api.presentation.ui.country.viewmodel.CountryDetailViewModel

/**
 * Navigation graph for the Countries feature.
 *
 * This NavHost manages two screens:
 * 1. CountriesScreen - Displays the list of countries.
 * 2. CountryDetailScreen - Displays details of a selected country.
 *
 * Uses Hilt for injecting ViewModels.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CountryNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Screens.CountriesScreen.route
    ) {
        /**
         * Shows a list of countries. Clicking a country navigates to CountryDetailScreen.
         */
        composable(route = Screens.CountriesScreen.route) { backStackEntry ->
            val viewModel: CountriesViewModel = hiltViewModel(backStackEntry)
            CountriesScreen(
                navController = navController, viewModel = viewModel
            )
        }

        /**
         * Shows details of a specific country.
         * Requires "country_code" as a navigation argument.
         */
        composable(
            route = Screens.CountryDetailScreen.route, arguments = listOf(
                navArgument("country_code") { type = NavType.StringType })
        ) { backStackEntry ->
            val countryCode = backStackEntry.arguments?.getString("country_code") ?: ""
            val viewModel: CountryDetailViewModel = hiltViewModel(backStackEntry)
            CountryDetailScreen(
                countryCode = countryCode, viewModel = viewModel
            )
        }
    }
}