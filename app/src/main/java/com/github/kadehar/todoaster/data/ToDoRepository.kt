package com.github.kadehar.todoaster.data

import androidx.lifecycle.LiveData
import com.github.kadehar.todoaster.data.local.ToDoEntity

interface ToDoRepository {
    fun all(): LiveData<List<ToDoEntity>>
    suspend fun create(data: ToDoEntity)
    suspend fun edit(data: ToDoEntity)
    suspend fun delete(data: ToDoEntity)
    suspend fun deleteAll()
    fun search(query: String): LiveData<List<ToDoEntity>>
    fun sortByHighPriority(): LiveData<List<ToDoEntity>>
    fun sortByLowPriority(): LiveData<List<ToDoEntity>>
}