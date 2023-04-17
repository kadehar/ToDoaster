package com.github.kadehar.todoaster.data

import androidx.lifecycle.LiveData
import com.github.kadehar.todoaster.data.local.ToDoDAO
import com.github.kadehar.todoaster.data.local.ToDoEntity

class ToDoRepositoryImpl(private val dao: ToDoDAO) : ToDoRepository {
    override fun all(): LiveData<List<ToDoEntity>> = dao.all()

    override suspend fun create(data: ToDoEntity) = dao.create(data)

    override suspend fun edit(data: ToDoEntity) = dao.edit(data)

    override suspend fun delete(data: ToDoEntity) = dao.delete(data)

    override suspend fun deleteAll() = dao.deleteAll()

    override fun search(query: String): LiveData<List<ToDoEntity>> = dao.search(query)

    override fun sortByHighPriority(): LiveData<List<ToDoEntity>> = dao.sortByHighPriority()

    override fun sortByLowPriority(): LiveData<List<ToDoEntity>> = dao.sortByLowPriority()
}