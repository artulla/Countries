package com.graphql.country_api.presentation.navigation

import com.graphql.country_api.presentation.navigation.Screens.CountryDetailScreen.passCountryCode


/**
 * Represents the navigation screens available in the application.
 *
 * This sealed class is used to define all navigation destinations in a type-safe way,
 * preventing hardcoded strings spread across the codebase.
 *
 * Each screen has an associated [route] string which is used by the NavHost.
 * Dynamic arguments can be passed using helper functions (e.g. [CountryDetailScreen.passCountryCode]).
 */
sealed class Screens(val route: String) {

    /**
     * Countries list screen.
     */
    object CountriesScreen : Screens(Routes.COUNTRIES)

    /**
     * Country detail screen.
     *
     * @property passCountryCode Builds the route string with the given [code] argument
     * so it can be used with [NavController.navigate].
     */
    object CountryDetailScreen : Screens("${Routes.COUNTRY_DETAIL}/{${NavArgs.COUNTRY_CODE}}") {
        fun passCountryCode(code: String) = "${Routes.COUNTRY_DETAIL}/$code"
    }
}

/**
 * Centralized navigation route constants.
 *
 * These constants define unique identifiers for each screen and should be used
 * in [Screens] and [NavHost] setup to avoid hardcoding route strings.
 */
object Routes {
    const val COUNTRIES = "countries_screen"
    const val COUNTRY_DETAIL = "country_detail_screen"
}

/**
 * Navigation arguments used in route definitions.
 *
 * Keeps argument names consistent across screens.
 */
object NavArgs {
    const val COUNTRY_CODE = "country_code"
}