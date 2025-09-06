package com.graphql.country_api.presentation.base.compose_view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.graphql.country_api.presentation.base.theme.CountryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonToolbar(
    title: String, onSearchTextChanged: (String) -> Unit, onBackClick: (() -> Unit)? = null
) {
    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    CountryTheme {
        TopAppBar(title = {
            if (isSearching) {
                OutlinedTextField(
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    value = searchText,
                    shape = RoundedCornerShape(28.dp),
                    onValueChange = {
                        searchText = it
                        onSearchTextChanged(it)
                    },
                    placeholder = { Text("Search by country...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(title)
            }
        }, navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        }, actions = {
            if (isSearching) {
                IconButton(onClick = {
                    isSearching = false
                    searchText = ""
                    onSearchTextChanged("")
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Close Search")
                }
            } else {
                IconButton(onClick = { isSearching = true }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        })
    }
}
