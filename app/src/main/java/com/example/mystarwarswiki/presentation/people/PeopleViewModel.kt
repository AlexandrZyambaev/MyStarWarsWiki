package com.example.mystarwarswiki.presentation.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystarwarswiki.domain.repository.PersonRepository
import com.example.mystarwarswiki.domain.usecase.GetPeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    getPeopleUseCase: GetPeopleUseCase,
    private val repository: PersonRepository
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val searchQuery = MutableStateFlow("")
    private val error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<PeopleUiState> = combine(
        getPeopleUseCase(),
        isLoading,
        error,
        searchQuery
    ) { people, loading, errorText, query ->
        val filtered = if (query.isBlank()) {
            people
        } else {
            people.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }

        PeopleUiState(
            isLoading = loading,
            people = filtered,
            error = errorText,
            searchQuery = query
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PeopleUiState()
    )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            try {
                repository.refreshPeople()
            } catch (e: Exception) {
                error.value = "Couldn't update data"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun onSearchChange(query: String) {
        searchQuery.value = query
    }
}