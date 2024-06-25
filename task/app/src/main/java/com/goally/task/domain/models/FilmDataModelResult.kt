package com.goally.task.domain.models


data class FilmDataModelResult(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Long>,
    val id: Long,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val first_air_date: String,
    val name: String,
    val vote_average: Double,
    val vote_count: Long
)