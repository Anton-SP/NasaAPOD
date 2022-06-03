package com.example.nasaapod.ui.notepad

import android.util.Log
import androidx.lifecycle.*
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


    private val _currentData = MutableLiveData(mutableListOf<AdapterItem>())
    val currentdData: LiveData<MutableList<AdapterItem>> = _currentData

    private val _editedList = MutableLiveData(mutableListOf<AdapterItem>())
    val editedList: LiveData<MutableList<AdapterItem>> = _editedList

    private val _id = MutableLiveData(-1)
    val id: LiveData<Int> = _id


    fun setEdited(list:MutableList<AdapterItem>){
        _editedList.value = list

    }

    fun setData(list:MutableList<AdapterItem>){
        _currentData.value = list
    }

    fun setData(){
        _currentData.value = mutableListOf(
            TextItem("id1", "One"),
            TextItem("id2", "two"),
            TextItem("id3", "three"),
            ImageItem("id3", R.drawable.test_image),
            TextItem("id4", "four"),
            TextItem("id5", "five"),
            ImageItem("id6", R.drawable.test_image),
        )
    }

    fun setId(id:Int){
        _id.value=id
    }

    fun clearId(){
        _id.value=-1
    }


        class NotebookViewModelFactory() : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                NotebookViewModel() as T

        }

}