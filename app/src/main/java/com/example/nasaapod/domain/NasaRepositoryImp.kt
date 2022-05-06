package com.example.nasaapod.domain

import com.example.nasaapod.api.ApodResponse
import com.example.nasaapod.api.NasaApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val api = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://api.nasa.gov/")
    .client(OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }
        .build()
    )
    .build()
    .create(NasaApi::class.java)

/**
ключ по хорошему не нужно передавать но для удобства учебных целей оставил так
 */

class NasaRepositoryImp:NasaRepository {
    override suspend fun Apod(): ApodResponse = api.getAPOD("GQr3kQbJTcVYc72YNrRg8GCNKbT6s618nfnS5COB")
}