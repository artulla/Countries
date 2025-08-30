package com.graphql.country_api.domain.usecase

import com.graphql.country_api.CountryDetailsQuery
import com.graphql.country_api.core.ApiResult
import com.graphql.country_api.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

/**
 * Use case responsible for fetching detailed information of a specific country.
 *
 * This use case interacts with the [CountryRepository] to fetch
 * detailed country data from the data layer and exposes it as a [Flow]
 * wrapped in an [ApiResult] for state management (Loading, Success, Error).
 *
 * @property repository The [CountryRepository] instance used to fetch data.
 */
class CountryDetailUseCase @Inject constructor(
    private val repository: CountryRepository
) {

    /**
     * Invokes the use case to fetch details of a country by its [countryCode].
     *
     * @param countryCode The ISO country code (e.g., "IN" for India, "US" for United States).
     * @return A [Flow] that emits:
     *  - [ApiResult.Loading] when the request starts
     *  - [ApiResult.Success] with the [CountryDetailsQuery.Country] when successful
     *  - [ApiResult.Error] with an error message if an exception occurs
     */
    operator fun invoke(countryCode: String): Flow<ApiResult<CountryDetailsQuery.Country?>> = flow {
        // Step 1: Emit loading state before starting the request
        emit(ApiResult.Loading)
        // Step 2: Fetch country details from the repository
        val country = repository.fetchCountryDetails(countryCode)
        // Step 3: Emit the success state with the retrieved data
        emit(ApiResult.Success(country))
    }.catch { e ->
        // Step 4: Emit error state with message in case of failure
        emit(ApiResult.Error(e.message ?: "Unknown error"))
    }.onCompletion { cause ->
        // Step 5: Optional - handle flow completion
        if (cause == null) {
            println("Flow completed successfully ✅")
        } else {
            println("Flow completed with error: ${cause.message}")
            emit(ApiResult.Error(cause.message ?: "Unknown error"))
        }
    }
}