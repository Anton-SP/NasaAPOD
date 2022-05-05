package com.example.nasaapod.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.domain.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(val repository: NasaRepository) : ViewModel() {

    val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    val _explanation: MutableStateFlow<String?> = MutableStateFlow(null)
    val explanation: Flow<String?> = _explanation

    private val _error:MutableSharedFlow<String> = MutableSharedFlow()
    val error:Flow<String> = _error
////////



    fun requestApod() {

        _loading.value = true

        viewModelScope.launch {
            try {
                val url = repository.Apod().url
                _image.emit(url)

                val info = repository.Apod().explanation
                _explanation.emit(info)

            } catch (exc: IOException) {
                _error.emit("Error from flow ")
            }

            _loading.emit(false)
        }
    }

}

class MainViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(repository) as T

}