package com.example.mystarwarswiki.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystarwarswiki.domain.repository.PersonRepository
import com.example.mystarwarswiki.domain.usecase.GetPersonByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPersonByIdUseCase: GetPersonByIdUseCase,
    private val repository: PersonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState(isLoading = true))
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        val personId = savedStateHandle.get<String>("personId")?.toIntOrNull()
        if (personId != null) {
            loadPerson(personId)
        } else {
            _uiState.value = DetailUiState(
                isLoading = false,
                error = "invalid character id"
            )
        }
    }

    private fun loadPerson(id: Int) {
        viewModelScope.launch {
            try {
                val person = getPersonByIdUseCase(id)

                if (person == null) {
                    _uiState.value = DetailUiState(
                        isLoading = false,
                        error = "The character was not found"
                    )
                    return@launch
                }

                val speciesNames = repository.getSpeciesNames(person.species)
                val filmTitles = repository.getFilmTitles(person.films)

                _uiState.value = DetailUiState(
                    isLoading = false,
                    person = person,
                    speciesNames = speciesNames,
                    filmTitles = filmTitles
                )
            } catch (_: Exception) {
                _uiState.value = DetailUiState(
                    isLoading = false,
                    error = "Failed to upload character details"
                )
            }
        }
    }
}