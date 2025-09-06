package com.graphql.country_api.presentation.ui.country.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.graphql.country_api.CountriesQuery
import com.graphql.country_api.core.ApiResult
import com.graphql.country_api.domain.model.Continent
import com.graphql.country_api.domain.usecase.CountriesUseCase
import com.graphql.country_api.domain.usecase.CountryDetailUseCase
import com.graphql.country_api.presentation.base.BaseViewModel
import com.graphql.country_api.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the list of countries.
 *
 * Fetches data using [CountryDetailUseCase] and exposes it as [StateFlow] for UI consumption.
 * Handles loading, success, error, and no internet states.
 *
 * @property context Application context injected via Hilt
 * @property networkHelper Utility class to check network connectivity
 * @property countriesUseCase Use case to fetch country list from repository layer
 */
@HiltViewModel
class CountriesViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val networkHelper: NetworkHelper,
    private val countriesUseCase: CountriesUseCase
) : BaseViewModel() {

    var showContinentDialog by mutableStateOf(true)

    private val _selectedContinentDialog = MutableStateFlow<Continent?>(null)
    val selectedContinentDialog: StateFlow<Continent?> = _selectedContinentDialog

    private val _searchCountry = MutableStateFlow("")
    val searchCountry: StateFlow<String> = _searchCountry

    private val _countries = MutableStateFlow<ApiResult<MutableList<CountriesQuery.Country>>>(
        ApiResult.Loading
    )

    /** Exposed StateFlow for observing the list of countries in UI.*/
    val countries: StateFlow<ApiResult<MutableList<CountriesQuery.Country>>> get() = _countries

    fun closeDialog() {
        showContinentDialog = false
    }

    fun setSelectedContinent(continent: Continent) {
        _selectedContinentDialog.value = continent
    }

    fun setSearchCountry(query: String) {
        _searchCountry.value = query
    }

    fun fetchCountries(continentCode: String) {
        viewModelScope.launch {
            if (networkHelper.isInternetAvailable()) {
                countriesUseCase(continentCode).collect { result ->
                    _countries.value = result
                }
            } else {
                _countries.value = ApiResult.NoInternetConnection("No internet connection")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _selectedContinentDialog.value = null
        _searchCountry.value = ""
        _countries.value = ApiResult.Loading
    }
}