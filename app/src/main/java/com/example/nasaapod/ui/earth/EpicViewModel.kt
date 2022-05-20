package com.example.nasaapod.ui.earth

import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.api.epic.EpicResponseDTO
import com.example.nasaapod.domain.EpicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException


class EpicViewModel(val repository: EpicRepository) : ViewModel() {
    val _listId = MutableStateFlow(0)
    val lsitId: Flow<Int?> = _listId

    val _listLenght = MutableStateFlow(0)
    val listLenght: Flow<Int?> = _listLenght

    val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    val _identifier: MutableStateFlow<String?> = MutableStateFlow(null)
    val identifier: Flow<String?> = _identifier

    val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    val _date: MutableStateFlow<String?> = MutableStateFlow(null)
    val date: Flow<String?> = _date

    val _epicList : MutableSharedFlow<List<EpicResponseDTO?>> = MutableSharedFlow()
    val epicList: Flow<List<EpicResponseDTO?>> = _epicList


    fun requestEpic() {

        _loading.value = true

        viewModelScope.launch {
            try {

                val epic = repository.Epic()
                _epicList.emit(epic)

            /*    val url = repository.Epic()[].image
                _image.emit(url)

                val identifier = repository.Epic()[].identifier
               _identifier.emit(identifier)

                val lenght = repository.Epic().count()
                _listLenght.emit(lenght)*/


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



