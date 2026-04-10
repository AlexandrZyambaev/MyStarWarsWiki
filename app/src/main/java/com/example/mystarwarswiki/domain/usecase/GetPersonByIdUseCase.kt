package com.example.mystarwarswiki.domain.usecase

import com.example.mystarwarswiki.domain.model.Person
import com.example.mystarwarswiki.domain.repository.PersonRepository

class GetPersonByIdUseCase(private val repository: PersonRepository) {
    suspend operator fun invoke(id: Int): Person? {
        return repository.getPersonById(id)
    }
}