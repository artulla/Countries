package com.graphql.country_api.presentation.ui.country.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graphql.country_api.CountryDetailsQuery
import com.graphql.country_api.core.ApiResult
import com.graphql.country_api.domain.usecase.CountryDetailUseCase
import com.graphql.country_api.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing country details.
 *
 * Fetches data using [CountryDetailUseCase] and exposes it as [StateFlow] for UI consumption.
 * Handles loading, success, error, and no internet states.
 *
 * @property context Application context injected via Hilt
 * @property savedStateHandle Used to persist/retrieve UI state such as country code
 * @property networkHelper Utility class to check network connectivity
 * @property countryDetailUseCase Use case to fetch country details from repository layer
 */
@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val savedStateHandle: SavedStateHandle,
    private val networkHelper: NetworkHelper,
    private val countryDetailUseCase: CountryDetailUseCase
) : ViewModel() {

    companion object {
        const val COUNTRY_CODE_KEY = "country_code"
    }

    private val _countryDetails = MutableStateFlow<ApiResult<CountryDetailsQuery.Country?>>(
        ApiResult.Loading
    )

    /** Exposed StateFlow for observing country details in UI */
    val countryDetails: StateFlow<ApiResult<CountryDetailsQuery.Country?>> get() = _countryDetails

    init {
        val countryCode: String = savedStateHandle[COUNTRY_CODE_KEY] ?: "IN"
        fetchCountryDetails(countryCode)
    }

    fun fetchCountryDetails(countryCode: String) {
        viewModelScope.launch {
            if (networkHelper.isInternetAvailable()) {
                try {
                    countryDetailUseCase(countryCode).collect { result ->
                        _countryDetails.value = result
                    }
                } catch (e: Exception) {
                    _countryDetails.value = ApiResult.Error(e.message ?: "Unknown error")
                }
            } else {
                _countryDetails.value = ApiResult.NoInternetConnection("No internet connection")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _countryDetails.value = ApiResult.Loading
    }
}