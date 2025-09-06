package com.graphql.country_api.domain.usecase

import com.graphql.country_api.CountriesQuery
import com.graphql.country_api.core.ApiResult
import com.graphql.country_api.domain.model.CountryEntity
import com.graphql.country_api.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

/**
 * Use case responsible for fetching and filtering countries based on their continent code.
 *
 * This class interacts with the [CountryRepository] to retrieve the list of countries,
 * then filters them according to the given continent code.
 *
 * @property repository The repository that provides access to countries data.
 */
class CountriesUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    /**
     * Executes the use case.
     *
     * @param continentCode The continent code used for filtering countries
     * (e.g., "AS" for Asia, "EU" for Europe).
     * @return A [Flow] that emits:
     * - [ApiResult.Loading] while fetching,
     * - [ApiResult.Success] with the filtered list of countries,
     * - [ApiResult.Error] if an exception occurs.
     */
    operator fun invoke(continentCode: String): Flow<ApiResult<MutableList<CountriesQuery.Country>>> =
        flow {
            // Step 1: Emit loading state
            emit(ApiResult.Loading)

            // Step 2: Fetch all countries from repository
            val countries =
                repository.fetchCountries() // Assuming this returns List<CountriesQuery.Country>

            // Step 3: Filter countries by continent code
            val filteredCountries = countries.filter {
                it.continent.code.equals(continentCode, ignoreCase = true)
            }

            // Map and insert into the database
            val mappedCountries = filteredCountries.map { country ->
                CountryEntity(
                    continentName = country.continent.name,
                    continentCode = country.continent.code,
                    capital = country.capital,
                    code = country.code,
                    phone = country.phone,
                    emoji = country.emoji,
                    name = country.name
                )
            }.toMutableList()
            repository.insertCountries(mappedCountries) // This is a suspend function

            // Step 4: Emit success with filtered list, converting to MutableList
            emit(ApiResult.Success(filteredCountries.toMutableList())) // <-- CORRECTED HERE
        }.catch { e ->
            // Step 5: Handle exceptions and emit error state
            println("Flow encountered an error: ${e.message}") // Log the error
            emit(ApiResult.Error(e.message ?: "Unknown error occurred"))
        }.onCompletion { cause ->
            // Step 6: Optional - handle flow completion (for side-effects like logging)
            if (cause == null) {
                println("Flow completed successfully ✅")
            } else {
                println("Flow completed with an upstream error (should have been caught by .catch): ${cause.message}")
            }
        }
}