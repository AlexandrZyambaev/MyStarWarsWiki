package com.example.mystarwarswiki.data.repository

import com.example.mystarwarswiki.data.local.dao.PersonDao
import com.example.mystarwarswiki.data.local.entity.PersonEntity
import com.example.mystarwarswiki.data.mapper.toDomain
import com.example.mystarwarswiki.data.mapper.toEntity
import com.example.mystarwarswiki.data.remote.api.StarWarsApi
import com.example.mystarwarswiki.domain.model.Person
import com.example.mystarwarswiki.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val api: StarWarsApi,
    private val dao: PersonDao
) : PersonRepository {

    override fun getPeople(): Flow<List<Person>> {
        return dao.getPeople().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshPeople() {
        try {
            val allPeople = mutableListOf<PersonEntity>()
            var page = 1

            while (true) {
                val response = api.getPeople(page)
                val pageItems = response.results.map { dto ->
                    dto.toEntity()
                }

                allPeople.addAll(pageItems)

                if (response.next == null) {
                    break
                }

                page++
            }

            dao.clearAll()
            dao.insertAll(allPeople)

        } catch (_: Exception) {
            // если нет сети, остаётся кеш
        }
    }

    override suspend fun getPersonById(id: Int): Person? {
        return dao.getPersonById(id)?.toDomain()
    }

    override suspend fun getSpeciesNames(urls: List<String>): List<String> {
        if (urls.isEmpty()) return listOf("Unknown")

        return urls.mapNotNull { url ->
            try {
                api.getSpeciesByUrl(url).name
            } catch (_: Exception) {
                null
            }
        }.ifEmpty { listOf("Unknown") }
    }

    override suspend fun getFilmTitles(urls: List<String>): List<String> {
        if (urls.isEmpty()) return emptyList()

        return urls.mapNotNull { url ->
            try {
                api.getFilmByUrl(url).title
            } catch (_: Exception) {
                null
            }
        }
    }
}