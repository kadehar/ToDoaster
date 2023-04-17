package com.github.kadehar.todoaster.domain

import androidx.lifecycle.LiveData
import com.github.kadehar.todoaster.domain.model.ToDo

interface ToDoInteractor {
    fun all(): LiveData<List<ToDo>>
    suspend fun create(data: ToDo)
    suspend fun edit(data: ToDo)
    suspend fun delete(data: ToDo)
    suspend fun deleteAll()
    fun search(query: String): LiveData<List<ToDo>>
    fun sortByHighPriority(): LiveData<List<ToDo>>
    fun sortByLowPriority(): LiveData<List<ToDo>>
}