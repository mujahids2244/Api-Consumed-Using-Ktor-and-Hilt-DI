package com.goally.task.domain.models



data class ResponseKtor<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorModel? = null
)