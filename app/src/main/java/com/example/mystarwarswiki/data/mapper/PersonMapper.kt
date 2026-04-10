package com.example.mystarwarswiki.data.mapper

import com.example.mystarwarswiki.data.local.entity.PersonEntity
import com.example.mystarwarswiki.data.remote.dto.PersonDto
import com.example.mystarwarswiki.domain.model.Person

private const val SEPARATOR = "|"

fun PersonDto.toEntity(): PersonEntity {
    return PersonEntity(
        id = extractIdFromUrl(url),
        name = name,
        height = height,
        mass = mass,
        hairColor = hairColor,
        skinColor = skinColor,
        eyeColor = eyeColor,
        birthYear = birthYear,
        gender = gender,
        species = species.joinToString(SEPARATOR),
        films = films.joinToString(SEPARATOR)
    )
}

fun PersonEntity.toDomain(): Person {
    return Person(
        id = id,
        name = name,
        height = height,
        mass = mass,
        hairColor = hairColor,
        skinColor = skinColor,
        eyeColor = eyeColor,
        birthYear = birthYear,
        gender = gender,
        species = if (species.isBlank()) emptyList() else species.split(SEPARATOR),
        films = if (films.isBlank()) emptyList() else films.split(SEPARATOR)
    )
}

private fun extractIdFromUrl(url: String): Int {
    return url.trimEnd('/').substringAfterLast('/').toInt()
}