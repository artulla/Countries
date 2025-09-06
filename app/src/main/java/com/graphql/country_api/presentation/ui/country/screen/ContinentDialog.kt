package com.graphql.country_api.presentation.ui.country.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.graphql.country_api.domain.model.Continent

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContinentDialog(
    continents: Array<Continent> = Continent.values(),
    onDismissRequest: () -> Unit,
    onSelect: (Continent) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(continents.size) { index ->
                    val continent = continents[index]
                    Text(
                        text = continent.displayName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelect(continent)
                                onDismissRequest()
                            }
                            .padding(12.dp))
                }
            }
        }
    }
}
