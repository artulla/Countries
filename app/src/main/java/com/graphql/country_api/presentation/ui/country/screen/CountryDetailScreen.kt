package com.graphql.country_api.presentation.ui.country.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.graphql.country_api.core.ApiResult
import com.graphql.country_api.presentation.ui.country.viewmodel.CountryDetailViewModel

@Composable
fun CountryDetailScreen(
    countryCode: String, viewModel: CountryDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Only set the countryCode in SavedStateHandle once
    LaunchedEffect(Unit) {
        viewModel.savedStateHandle[CountryDetailViewModel.COUNTRY_CODE_KEY] = countryCode
    }

    // Collect the country details from StateFlow
    val countryDetailState by viewModel.countryDetails.collectAsStateWithLifecycle()

    when (val state = countryDetailState) {
        is ApiResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }
        }

        is ApiResult.Success -> {
            val country = state.data
            country?.let {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(text = "Name: ${it.name} (${it.code})")
                    Text(text = "Capital: ${it.capital}")
                    Text(text = "Currency: ${it.currencies.joinToString()}")
                    Text(text = "Emoji: ${it.emoji}")
                }
            }
        }

        is ApiResult.Error -> {
            val message = state.error ?: "Something went wrong"
            Log.d("CountryDetailScreen", message)
        }

        is ApiResult.NoInternetConnection -> {
            val message = state.message
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}