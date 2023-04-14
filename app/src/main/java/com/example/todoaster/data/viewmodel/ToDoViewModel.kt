package com.example.todoaster.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoaster.data.ToDoDatabase
import com.example.todoaster.data.models.ToDoData
import com.example.todoaster.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val todoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository = ToDoRepository(todoDao)
    val fetchAllData: LiveData<List<ToDoData>> = repository.findAll
    val sortByHighPriority: LiveData<List<ToDoData>> = repository.sortByHighPriority
    val sortByLowPriority: LiveData<List<ToDoData>> = repository.sortByLowPriority

    fun addNew(toDoData: ToDoData) = viewModelScope.launch(Dispatchers.IO) {
        repository.addNew(toDoData)
    }

    fun update(toDoData: ToDoData) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(toDoData)
    }

    fun delete(toDoData: ToDoData) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(toDoData)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun search(query: String): LiveData<List<ToDoData>> = repository.search(query)
}