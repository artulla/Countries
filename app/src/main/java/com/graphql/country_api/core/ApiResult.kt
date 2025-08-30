package com.graphql.country_api.core

/**
 * A sealed class representing the result of an API call.
 *
 * This class encapsulates the different possible states of a network request,
 * allowing the UI or repository layer to handle them appropriately.
 *
 * @param T The type of the data returned in case of a successful API call.
 */
sealed class ApiResult<out T> {

    /**
     * Represents a successful API response containing the requested [data].
     *
     * @param data The payload returned by the API.
     */
    data class Success<out T>(val data: T) : ApiResult<T>()

    /**
     * Represents an API error response with an error [error].
     *
     * @param error The description of the error.
     */
    data class Error<out T>(val error: String) : ApiResult<T>()

    /**
     * Represents a state where there is no internet connection.
     *
     * @param message A message indicating that the internet is not available.
     */
    data class NoInternetConnection(val message: String) : ApiResult<Nothing>()

    /**
     * Represents a loading state when the API request is in progress.
     */
    object Loading : ApiResult<Nothing>()
}

