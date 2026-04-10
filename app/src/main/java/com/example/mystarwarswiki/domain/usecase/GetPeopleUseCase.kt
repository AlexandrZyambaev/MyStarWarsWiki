package com.example.mystarwarswiki.domain.usecase

import com.example.mystarwarswiki.domain.repository.PersonRepository

class GetPeopleUseCase(private val repository: PersonRepository) {
    operator fun invoke() = repository.getPeople()
}