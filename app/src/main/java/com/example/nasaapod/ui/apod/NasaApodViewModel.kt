package com.example.nasaapod.ui.apod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.domain.NasaApodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class NasaApodViewModel(val repository: NasaApodRepository) : ViewModel() {

    val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    val _title: MutableStateFlow<String?> = MutableStateFlow(null)
    val title: Flow<String?> = _title

    val _explanation: MutableStateFlow<String?> = MutableStateFlow(null)
    val explanation: Flow<String?> = _explanation

    val _date: MutableStateFlow<Date?> = MutableStateFlow(null)
    val date: Flow<Date?> = _date

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    fun requestApod() {

        _loading.value = true

        viewModelScope.launch {
            try {
                val url = repository.Apod().url
                _image.emit(url)

                val title = repository.Apod().title
                _title.emit(title)

                val info = repository.Apod().explanation
                _explanation.emit(info)

                val date = repository.Apod().date
                _date.emit(date)

            } catch (exc: IOException) {
                _error.emit("Error loading APOD API ")
            }

            _loading.emit(false)
        }
    }



    class NasaApodViewModelFactory(private val repository: NasaApodRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NasaApodViewModel(repository) as T

    }

}

