package com.example.nasaapod.domain

import com.example.nasaapod.api.ApodResponse

interface NasaRepository {
    suspend fun Apod ():ApodResponse
}