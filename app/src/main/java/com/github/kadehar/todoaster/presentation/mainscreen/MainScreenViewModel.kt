package com.github.kadehar.todoaster.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kadehar.todoaster.domain.ToDoInteractor
import com.github.kadehar.todoaster.domain.model.ToDo
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val interactor: ToDoInteractor
) : ViewModel() {

    fun getAll() = interactor.all()

    fun delete(data: ToDo) = viewModelScope.launch {
        interactor.delete(data)
    }

    fun create(data: ToDo) = viewModelScope.launch {
        interactor.create(data)
    }

    fun deleteAll() = viewModelScope.launch {
        interactor.deleteAll()
    }

    fun search(query: String) = interactor.search(query)
    fun sortByHighPriority() = interactor.sortByHighPriority()
    fun sortByLowPriority() = interactor.sortByLowPriority()
}