package com.example.mystarwarswiki.domain.repository

import com.example.mystarwarswiki.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getPeople(): Flow<List<Person>>
    suspend fun refreshPeople()
    suspend fun getPersonById(id: Int): Person?

    suspend fun getSpeciesNames(urls: List<String>): List<String>
    suspend fun getFilmTitles(urls: List<String>): List<String>
}