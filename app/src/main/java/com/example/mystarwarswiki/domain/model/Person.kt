package com.example.mystarwarswiki.domain.model

data class Person(
    val id: Int,
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val species: List<String>,
    val films: List<String>
)