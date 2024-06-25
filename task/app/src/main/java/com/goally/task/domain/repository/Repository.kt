package com.goally.task.domain.repository


import android.util.Log
import com.goally.task.domain.models.ErrorModel
import com.goally.task.domain.models.ResponseKtor
import com.goally.task.domain.repository.constant.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject


class Repository @Inject constructor( val httpClient: HttpClient) {


    suspend inline fun <reified T : Any> getFilmData(key:String,page:Int): ResponseKtor<T> {

        return try {
            val response = httpClient.get<T>(Constants.endPoint){
                parameter("api_key", key)
                parameter("page", page)
            }
            ResponseKtor(success = true, data = response)
        } catch (e: Exception) {
            Log.e("api failed",  e.message.toString() )
             ResponseKtor(success = false, error = ErrorModel(500, "An error occurred"))

        }
    }
}









