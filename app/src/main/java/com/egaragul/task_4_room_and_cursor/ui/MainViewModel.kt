package com.egaragul.task_4_room_and_cursor.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egaragul.task_4_room_and_cursor.domain.data.Animal
import com.egaragul.task_4_room_and_cursor.domain.repository.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = AnimalRepository()

    fun save(name: String, age: Int, breed: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.save(name, age, breed)
        }
    }

    private val _list = MutableLiveData<List<Animal>>(emptyList())
    val list: LiveData<List<Animal>>
        get() = _list

    fun getList() {
        viewModelScope.launch(Dispatchers.IO) {
            sort(repository.read())
        }
    }

    fun update(id: Int, name: String, age: Int, breed: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.update(Animal(id, name, age, breed))) {
                getList()
            }
        }

    }

    fun delete(position: Int) {
        _list.value?.let {
            val animal = it[position]
            val ml = mutableListOf<Animal>()
            ml.addAll(it)

            viewModelScope.launch(Dispatchers.IO) {
                repository.delete(animal)
                ml.removeAt(position)
                _list.postValue(ml)

            }


        }

    }

    private val _editedAnimal = MutableLiveData<Animal?>(null)
    val editedAnimal: LiveData<Animal?>
        get() = _editedAnimal

    fun getItemById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getItemById(id)?.let {
                _editedAnimal.postValue(it)
            }
        }
    }

    private var selectedSortItem = -1

    fun getSorItem() = selectedSortItem
    fun setSelectedOrder(selected: Int) {
        selectedSortItem = selected
        sort(_list.value!!)
    }

    private fun sort(list: List<Animal>) {
        val sorted = list.sortedWith(
            compareBy {
                if (selectedSortItem == -1 || selectedSortItem == 0) {
                    Log.d("TAG", "getList: $selectedSortItem")
                    it.name
                } else if (selectedSortItem == 1) {
                    Log.d("TAG", "getList:$selectedSortItem")
                    it.age
                } else {
                    Log.d("TAG", "getList: $selectedSortItem")
                    it.breed
                }
            }
        )

        _list.postValue(sorted)
    }

    override fun onCleared() {
        repository.onDestroy()
        super.onCleared()
    }

    private var _isRoom = false
    val isRoom : Boolean
        get() = _isRoom
    fun setDbUsing(isRoom : Boolean) {
        _isRoom = isRoom
        repository.provideRoomUsing(!_isRoom)
    }
}