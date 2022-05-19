package com.example.nasaapod.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.domain.EpicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException


class EpicViewModel(val repository: EpicRepository) : ViewModel() {
    val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    val _identifier: MutableStateFlow<String?> = MutableStateFlow(null)
    val identifier: Flow<String?> = _identifier

    val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    val _date: MutableStateFlow<String?> = MutableStateFlow(null)
    val date: Flow<String?> = _date

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    fun requestEpic() {

        _loading.value = true

        viewModelScope.launch {
            try {
                val url = repository.Epic()[1].image
                _image.emit(url)

                val identifier = repository.Epic()[1].identifier
               _identifier.emit(identifier)

            } catch (exc: IOException) {
                _error.emit("Error from flow ")
            }

            _loading.emit(false)
        }
    }

    class EpicViewModelFactory(private val repository: EpicRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            EpicViewModel(repository) as T
    }

}



