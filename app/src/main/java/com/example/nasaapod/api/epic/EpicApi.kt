package com.example.nasaapod.api.epic

import retrofit2.http.GET

interface EpicApi {

    @GET("api/natural")
    suspend fun getEpic(): List<EpicResponseDTO>
}