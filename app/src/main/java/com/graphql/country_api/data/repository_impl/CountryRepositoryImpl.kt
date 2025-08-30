package com.graphql.country_api.data.repository_impl

import com.graphql.country_api.CountriesQuery
import com.graphql.country_api.CountryDetailsQuery
import com.graphql.country_api.data.local.room_db.CountriesDao
import com.graphql.country_api.data.remote.source.CountryDataSource
import com.graphql.country_api.domain.model.CountryEntity
import com.graphql.country_api.domain.repository.CountryRepository
import javax.inject.Inject

/**
 * Implementation of [CountryRepository] that fetches country data from a remote data source.
 *
 * This class interacts with [CountryDataSource] to retrieve both the full list of countries
 * and detailed information about a single country.
 *
 * @param countryDataSource The data source responsible for remote GraphQL API calls.
 */
class CountryRepositoryImpl @Inject constructor(
    private val countryDataSource: CountryDataSource, private val countriesDao: CountriesDao
) : CountryRepository {

    /**
     * Fetch the complete list of countries from the remote data source.
     *
     * @return A mutable list of [CountriesQuery.Country]. Returns an empty list if
     *         no data is available.
     */
    override suspend fun fetchCountries(): MutableList<CountriesQuery.Country> {
        val remoteCountries = countryDataSource.fetchCountries()
        return remoteCountries
    }

    /**
     * Fetch detailed information about a single country by its code.
     *
     * @param countryCode The unique code of the country (e.g., "US", "AE").
     * @return [CountryDetailsQuery.Country] containing detailed information.
     * @throws Exception If the remote call fails or the country is not found.
     */
    override suspend fun fetchCountryDetails(countryCode: String): CountryDetailsQuery.Country {
        return countryDataSource.fetchCountryDetails(countryCode)
    }

    override suspend fun insertCountries(mappedCountries: MutableList<CountryEntity>) {
        countriesDao.insertAll(mappedCountries)
    }
}