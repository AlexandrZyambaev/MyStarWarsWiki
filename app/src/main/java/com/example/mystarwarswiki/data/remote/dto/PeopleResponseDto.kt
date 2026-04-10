package com.example.mystarwarswiki.data.remote.dto

data class PeopleResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PersonDto>
)