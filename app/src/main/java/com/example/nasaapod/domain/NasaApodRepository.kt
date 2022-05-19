package com.example.nasaapod.domain

import com.example.nasaapod.api.ApodResponse

interface NasaApodRepository {
    suspend fun Apod ():ApodResponse

}