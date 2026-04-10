package com.example.mystarwarswiki.presentation.people

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mystarwarswiki.domain.model.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleScreen(
    onPersonClick: (Int) -> Unit,
    viewModel: PeopleViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Star Wars Characters") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = viewModel::onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search characters...") }
            )

            when {
                state.isLoading && state.people.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.people.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Ошибка подключения",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Button(
                                onClick = { viewModel.refresh() }
                            ) {
                                Text("Повторить попытку")
                            }
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.people) { person ->
                            PersonItem(
                                person = person,
                                onClick = { onPersonClick(person.id) }
                            )
                        }
                    }
                }
            }

            state.error?.let {
                Text(text = it)
            }
        }
    }
}

@Composable
private fun PersonItem(
    person: Person,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = person.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Height: ${person.height} cm, Mass: ${person.mass} kg, Hair: ${person.hairColor}, Eyes: ${person.eyeColor}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}