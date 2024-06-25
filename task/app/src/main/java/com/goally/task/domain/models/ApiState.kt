package com.goally.task.domain.models


sealed class ApiState {
    data class Success(val data: FilmDataModel) : ApiState()
    data class Error(val message: ErrorModel) : ApiState()
    data object Empty: ApiState()
}
