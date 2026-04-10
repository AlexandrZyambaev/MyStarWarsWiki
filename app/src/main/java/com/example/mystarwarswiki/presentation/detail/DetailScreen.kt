package com.example.mystarwarswiki.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.person?.name ?: "Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.person != null -> {
                val person = state.person!!
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    item {
                        Text("Basic Information",
                            modifier = Modifier.padding(top = 16.dp),
                            style = MaterialTheme.typography.titleMedium)
                    }

                    item {
                        InfoCard("Birth Year", person.birthYear)
                    }

                    item {
                        InfoCard("Height", "${person.height} cm")
                    }

                    item {
                        InfoCard("Mass", "${person.mass} kg")
                    }

                    item {
                        InfoCard("Gender", person.gender)
                    }

                    item {
                        Text("Species",
                            modifier = Modifier.padding(top = 16.dp),
                            style = MaterialTheme.typography.titleMedium)
                    }

                    item {
                        ListInfoCard(items = state.speciesNames)
                    }

                    item {
                        Text("Films",
                            modifier = Modifier.padding(top = 16.dp),
                            style = MaterialTheme.typography.titleMedium)
                    }

                    item {
                        ListInfoCard(items = state.filmTitles)
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.error ?: "no data")
                }
            }
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun ListInfoCard(
    items: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (items.isEmpty()) {
                Text(
                    text = "no data",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                items.forEachIndexed { index, item ->
                    Text(
                        text = "• $item",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    if (index != items.lastIndex) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    }
}