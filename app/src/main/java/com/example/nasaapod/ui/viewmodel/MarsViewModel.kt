package com.example.nasaapod.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.domain.MarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException

class MarsViewModel(val repository: MarsRepository) : ViewModel() {

    val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    val _title: MutableStateFlow<String?> = MutableStateFlow(null)
    val title: Flow<String?> = _title

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    fun requestMars() {

        _loading.value = true

        viewModelScope.launch {
            try {
                val url = repository.Mars().latestPhotos[0].imgSrc
                _image.emit(url)


                val title = repository.Mars().latestPhotos[0].id
                _title.emit(title)
            } catch (exc: IOException) {
                _error.emit("Error from flow mars")
            }
            _loading.emit(false)
        }


    }

    class MarsViewModelFactory(private val repository: MarsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MarsViewModel(repository) as T

    }

}