package com.example.nasaapod.ui.notepad

import androidx.annotation.DrawableRes

sealed class AdapterItem(val key: String)

data class TextItem(val id: String, val text: String, var update:Boolean) : AdapterItem(id)
data class ImageItem(val id: String, @DrawableRes val img: Int,var update:Boolean) : AdapterItem(id)