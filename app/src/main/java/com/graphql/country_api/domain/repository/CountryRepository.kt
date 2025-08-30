package com.graphql.country_api.domain.repository

import com.graphql.country_api.CountriesQuery
import com.graphql.country_api.CountryDetailsQuery
import com.graphql.country_api.data.local.room_db.CountriesDao
import com.graphql.country_api.domain.model.CountryEntity
import javax.inject.Inject

/**
 * Repository interface for fetching country data.
 * Provides methods to access both a list of countries and details of a single country.
 *
 * The implementation of this interface will handle data sources such as
 * remote GraphQL API or local Room database.
 */
interface CountryRepository {

    /**
     * Fetch the complete list of countries.
     *
     * @return A mutable list of [CountriesQuery.Country].
     *         This list may be empty if no data is available.
     */
    suspend fun fetchCountries(): MutableList<CountriesQuery.Country>


    /**
     * Fetch detailed information about a single country using its code.
     *
     * @param countryCode The unique country code (e.g., "US", "IN").
     * @return [CountryDetailsQuery.Country] object containing detailed information.
     *         May throw an exception if the country is not found or network fails.
     */
    suspend fun fetchCountryDetails(countryCode: String): CountryDetailsQuery.Country

    suspend fun insertCountries(mappedCountries: MutableList<CountryEntity>)
}
