package com.goally.task.presentation.di

import com.goally.task.data.network.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class CoreModule {

    @Provides
    @Singleton
    fun getHttpClient(httpClient: KtorClient): HttpClient = httpClient.ktorClient


}
