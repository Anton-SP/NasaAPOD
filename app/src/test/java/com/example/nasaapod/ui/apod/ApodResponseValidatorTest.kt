package com.example.nasaapod.ui.apod

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ApodResponseValidatorTest {

    @Test
    fun nasaApodUrlValidator_CorrectUrl_ReturnTrue() {
        assertTrue(ApodResponseValidator.isValidApodUrl("image.jpg"))
    }

    @Test
    fun nasaApodUrlValidator_InvalidUrl_ReturnFalse() {
        assertFalse(ApodResponseValidator.isValidApodUrl("image"))
    }

    @Test
    fun nasaApodUrlValidator_InvalidUrl2_ReturnFalse() {
        assertFalse(ApodResponseValidator.isValidApodUrl("image.mp4"))
    }

    @Test
    fun nasaApodUrlValidator_InvalidUrl3_ReturnFalse() {
        assertFalse(ApodResponseValidator.isValidApodUrl(".jpg.image"))
    }

}