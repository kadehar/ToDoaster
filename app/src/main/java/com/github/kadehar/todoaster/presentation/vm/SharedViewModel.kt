package com.github.kadehar.todoaster.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kadehar.todoaster.domain.ToDoInteractor
import com.github.kadehar.todoaster.domain.model.ToDo
import kotlinx.coroutines.launch

class SharedViewModel(
    private val interactor: ToDoInteractor
) : ViewModel() {

    fun create(toDo: ToDo) = viewModelScope.launch {
        interactor.create(toDo)
    }

    fun update(toDo: ToDo) = viewModelScope.launch {
        interactor.edit(toDo)
    }

    fun verify(title: String, desc: String): Boolean = !(title.isEmpty() || desc.isEmpty())
}