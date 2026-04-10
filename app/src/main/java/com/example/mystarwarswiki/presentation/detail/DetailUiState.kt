package com.example.mystarwarswiki.presentation.detail

import com.example.mystarwarswiki.domain.model.Person

data class DetailUiState(
    val isLoading: Boolean = false,
    val person: Person? = null,
    val speciesNames: List<String> = emptyList(),
    val filmTitles: List<String> = emptyList(),
    val error: String? = null
)