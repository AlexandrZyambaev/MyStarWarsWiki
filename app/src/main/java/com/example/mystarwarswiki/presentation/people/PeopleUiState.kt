package com.example.mystarwarswiki.presentation.people

import com.example.mystarwarswiki.domain.model.Person

data class PeopleUiState(
    val isLoading: Boolean = false,
    val people: List<Person> = emptyList(),
    val error: String? = null,
    val searchQuery: String = ""
)