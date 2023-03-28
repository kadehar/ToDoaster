package com.example.todoaster.data.repository

import androidx.lifecycle.LiveData
import com.example.todoaster.data.ToDoDAO
import com.example.todoaster.data.models.ToDoData

class ToDoRepository(private val dao: ToDoDAO) {

    val findAll: LiveData<List<ToDoData>> = dao.findAll()

    suspend fun addNew(toDoData: ToDoData) = dao.addNew(toDoData)

    suspend fun update(toDoData: ToDoData) = dao.update(toDoData)

    suspend fun delete(toDoData: ToDoData) = dao.delete(toDoData)
}