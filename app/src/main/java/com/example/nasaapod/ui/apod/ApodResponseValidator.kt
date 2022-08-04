package com.example.nasaapod.ui.apod

import com.example.nasaapod.api.apod.ApodResponse

class ApodResponseValidator(var apodResponse: ApodResponse) {

    companion object {
        fun isValidApodUrl(url:CharSequence?):Boolean{
            return url?.contains(".jpg") ?: false
        }

    }


}