package com.example.nasaapod.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    /**
     * APOD -  Astronomy Picture of the Day
     */
    @GET("planetary/apod")
    suspend fun getAPOD(@Query("api_key") key: String): ApodResponse

    /**
     * EPIC - Earth Polychromatic Imaging Camera
     */
/*    @GET("api/natural")
    suspend fun getEpic(): EpicResponse*/
}