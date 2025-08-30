package com.graphql.country_api.data.remote.source

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloException
import com.graphql.country_api.CountriesQuery
import com.graphql.country_api.CountryDetailsQuery
import javax.inject.Inject

/**
 * Remote data source for fetching country data using Apollo GraphQL.
 *
 * This class communicates directly with the GraphQL API via [ApolloClient].
 * Provides methods for fetching the full list of countries and details for a single country.
 *
 * @param apolloClient The Apollo GraphQL client for executing queries.
 */
class CountryDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {

    /**
     * Fetch the complete list of countries from the GraphQL API.
     *
     * @return A mutable list of [CountriesQuery.Country]. Returns an empty list if
     *         the API returns null or if an exception occurs.
     */
    suspend fun fetchCountries(): MutableList<CountriesQuery.Country> {
        return try {
            val response = apolloClient.query(CountriesQuery()).execute()
            response.data?.countries?.toMutableList() ?: mutableListOf()
        } catch (e: ApolloException) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    /**
     * Fetch detailed information for a single country by its code.
     *
     * @param countryCode The unique country code (e.g., "US", "AE").
     * @return [CountryDetailsQuery.Country] object containing detailed information.
     * @throws IllegalStateException if no country is found for the given code.
     * @throws ApolloException if the network call fails.
     */
    suspend fun fetchCountryDetails(countryCode: String): CountryDetailsQuery.Country {
        return try {
            val response = apolloClient.query(CountryDetailsQuery(countryCode)).execute()
            response.data?.country
                ?: throw IllegalStateException("Country not found for code: $countryCode")
        } catch (e: ApolloException) {
            e.printStackTrace()
            throw e
        }
    }
}
