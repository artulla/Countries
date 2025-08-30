package com.graphql.country_api.presentation.ui.country.screen

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.graphql.country_api.CountriesQuery
import com.graphql.country_api.core.ApiResult
import com.graphql.country_api.presentation.ui.country.viewmodel.CountriesViewModel

@Composable
fun CountriesScreen(
    navController: NavController, viewModel: CountriesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var showContinentDialog by remember { mutableStateOf(true) }
    val selectedContinent by viewModel.selectedContinentDialog.collectAsState()
    val searchCountry by viewModel.searchCountry.collectAsState()
    val countriesState by viewModel.countries.collectAsStateWithLifecycle()  // Collect the countries list from StateFlow

    if (showContinentDialog) {
        ContinentDialog(onDismissRequest = { showContinentDialog = false }, onSelect = { selected ->
            viewModel.setSelectedContinent(selected)
            showContinentDialog = false
        })
    }

    LaunchedEffect(selectedContinent) {
        selectedContinent?.let { continent ->
            viewModel.fetchCountries(continent.name)
        }
    }

    when (countriesState) {
        is ApiResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp) // reasonable size
                )
            }
        }

        is ApiResult.Success -> {
            val countries = (countriesState as ApiResult.Success).data
            val searchCountries = countries.filter { country ->
                country.name.contains(searchCountry, ignoreCase = true)
            }
            println("Country Size: ${searchCountries.size}")
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = searchCountry,
                    onValueChange = { viewModel.setSearchCountry(it) },
                    placeholder = { Text("Search by country") },
                    shape = RoundedCornerShape(28.dp),
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search country"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn {
                    items(searchCountries) { country ->
                        CountryItem(context = context, navController, country = country)
                    }
                }
            }
        }

        is ApiResult.Error -> {
            val message = (countriesState as ApiResult.Error).error
            Log.d("Error: ", message)
        }

        is ApiResult.NoInternetConnection -> {
            val message = (countriesState as ApiResult.NoInternetConnection).message
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun CountryItem(
    context: Context, navController: NavController, country: CountriesQuery.Country
) {
    // Get Flag image https://flagcdn.com/w320/ae.png, https://flagcdn.com/256x192/ae.png
    val flagUrl = "https://flagcdn.com/256x192/${country.code.lowercase()}.png"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), onClick = ({
            navController.navigate("country_detail_screen/${country.code}")

            /* val intent = Intent(context, CountryDetailActivity::class.java)
             val bundle = Bundle()
             bundle.putString(CountryDetailViewModel.COUNTRY_CODE_KEY, country.code)
             intent.putExtras(bundle)
             context.startActivity(intent)*/
            if (context is Activity) {
//                context.finish()
            }
            Toast.makeText(context, country.name, Toast.LENGTH_SHORT).show()
        }), elevation = CardDefaults.cardElevation(8.dp), // Shadow effect
        shape = MaterialTheme.shapes.medium // Rounded corners
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Flag Image on the left
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(flagUrl).crossfade(true)
                            .build()
                    ),
                    contentDescription = "Flag of ${country.name}",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 0.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp)
                ) {
                    Text(
                        text = "Country: ${country.name}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Capital: ${country.capital}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Continent: ${country.continent.name}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
//        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
        }
    }
}