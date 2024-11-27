package com.entropia.flightsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.entropia.flightsearch.R
import com.entropia.flightsearch.data.Airport

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: FlightSearchViewModel, modifier: Modifier = Modifier) {
    TextField(
        value = viewModel.userInput,
        onValueChange = { newValue ->
            viewModel.updateUserInput(newValue)
            viewModel.updateCurrentAirport(null)
            if (newValue.isEmpty()) {
                viewModel.clearSearchResultsList()
            } else {
                viewModel.getSearchResultsList(newValue)
                viewModel.updateInputPreferences(newValue)
            }
        },
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        placeholder = {
            Text(
                text = stringResource(id = R.string.enter_name),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            cursorColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun SearchResultList(
    viewModel: FlightSearchViewModel, modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        items(viewModel.flightSearchUi.suggestedAirportList) { airport ->
            AirportData(airport = airport, modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.updateCurrentAirport(airport = airport)
                    viewModel.getAllDestinationAirports()
                })
        }
    }
}


@Composable
fun AirportData(airport: Airport, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
    ) {
        Text(
            text = airport.iataCode, modifier = Modifier.padding(
                end = dimensionResource(
                    id = R.dimen.padding_small
                )
            ), style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = airport.name, style = MaterialTheme.typography.bodySmall
        )
    }
}