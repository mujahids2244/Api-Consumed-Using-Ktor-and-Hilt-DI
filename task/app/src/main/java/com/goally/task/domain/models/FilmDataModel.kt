package com.goally.task.domain.models


data class FilmDataModel(
    val page: Long,
    val results: List<FilmDataModelResult>,
    val totalPages: Long,
    val totalResults: Long,

)