package com.example.mystarwarswiki.data.remote.api

import com.example.mystarwarswiki.data.remote.dto.FilmDto
import com.example.mystarwarswiki.data.remote.dto.PeopleResponseDto
import com.example.mystarwarswiki.data.remote.dto.SpeciesDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface StarWarsApi {

    @GET("people/")
    suspend fun getPeople(
        @Query("page") page: Int
    ): PeopleResponseDto

    @GET
    suspend fun getSpeciesByUrl(
        @Url url: String
    ): SpeciesDto

    @GET
    suspend fun getFilmByUrl(
        @Url url: String
    ): FilmDto
}