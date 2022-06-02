package com.example.nasaapod.ui.notepad

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nasaapod.R
import com.example.nasaapod.ui.mars.MarsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * вьюмодель для обмена информацией между фрагментами при редактоировании
 * можно было бы просто биандл но не хочу
 * тем более пока у нас только заметки
 * а вдруг еще чего захотим
 * в вьюмодели как то проще прикрутить
 */


class NotebookViewModel : ViewModel() {



  /* private var _currentTextBuffer = MutableStateFlow("")
    val currentTextBuffer:Flow<String> = _currentTextBuffer*/

    private var _currentTextBuffer = MutableStateFlow(-1)
    val currentTextBuffer:Flow<Int> = _currentTextBuffer

    //var data: MutableList<AdapterItem> = mutableListOf()

    private var _currentData = MutableStateFlow<MutableList<AdapterItem>>(mutableListOf())
    val currentData:Flow<MutableList<AdapterItem>> = _currentData


    fun setBuffer(buffer:Int) {
        viewModelScope.launch {
            _currentTextBuffer.emit(buffer)
            Log.d("HAPPY","in view model = "+_currentTextBuffer.value)
            Log.d("HAPPY","in view model = "+_currentTextBuffer.value)
        }
    }

    fun clearBuffer(){
        viewModelScope.launch {
            _currentTextBuffer.emit(-1)
        }
    }

    class NotebookViewModelFactory() : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NotebookViewModel() as T
    }

    fun setBeginData(){
        viewModelScope.launch {
            _currentData.emit(mutableListOf(
                TextItem("id1", "One"),
                TextItem("id2", "two"),
                TextItem("id3", "three"),
                ImageItem("id3", R.drawable.test_image),
                TextItem("id4", "four"),
                TextItem("id5", "five"),
                ImageItem("id6", R.drawable.test_image),
            ))
        }

    }

    fun updateItem(newItem: AdapterItem){
        var copy = _currentData.value
        copy[_currentTextBuffer.value] = newItem
        viewModelScope.launch{
            _currentData.emit(copy)
        }

    }

    fun serData(newList:MutableList<AdapterItem>){
        viewModelScope.launch {
            _currentData.emit(newList)
            _currentTextBuffer.emit(-1)
        }

    }

}