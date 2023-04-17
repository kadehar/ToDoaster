package com.github.kadehar.todoaster.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.github.kadehar.todoaster.base.Mapper
import com.github.kadehar.todoaster.data.ToDoRepository
import com.github.kadehar.todoaster.data.local.ToDoEntity
import com.github.kadehar.todoaster.domain.model.ToDo

class ToDoInteractorImpl(
    private val repository: ToDoRepository,
    private val mapper: Mapper<ToDoEntity, ToDo>
) : ToDoInteractor {

    override fun all(): LiveData<List<ToDo>> = repository.all().map { data ->
        data.map { mapper.mapTo(it) }
    }

    override suspend fun create(data: ToDo) = repository.create(
        data = mapper.mapFrom(data)
    )

    override suspend fun edit(data: ToDo) = repository.edit(
        data = mapper.mapFrom(data)
    )

    override suspend fun delete(data: ToDo) = repository.delete(
        data = mapper.mapFrom(data)
    )

    override suspend fun deleteAll() = repository.deleteAll()

    override fun search(query: String): LiveData<List<ToDo>> = repository
        .search(query)
        .map { data ->
            data.map { mapper.mapTo(it) }
        }

    override fun sortByHighPriority(): LiveData<List<ToDo>> = repository
        .sortByHighPriority()
        .map { data ->
            data.map { mapper.mapTo(it) }
        }

    override fun sortByLowPriority(): LiveData<List<ToDo>> = repository
        .sortByLowPriority()
        .map { data ->
            data.map { mapper.mapTo(it) }
        }
}