package com.example.nasaapod.ui.notepad

import androidx.annotation.DrawableRes

sealed class AdapterItem(val key: String)

data class TextItem(val id: String, var text: String) : AdapterItem(id)
data class ImageItem(val id: String, @DrawableRes val img: Int) : AdapterItem(id)