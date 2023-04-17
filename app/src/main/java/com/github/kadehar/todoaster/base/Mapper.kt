package com.github.kadehar.todoaster.base

import com.github.kadehar.todoaster.data.local.ToDoEntity
import com.github.kadehar.todoaster.domain.model.ToDo

interface Mapper<T, R> {
    fun mapTo(data: T): R
    fun mapFrom(domain: R): T

    class Base : Mapper<ToDoEntity, ToDo> {
        override fun mapTo(data: ToDoEntity): ToDo = ToDo(
            id = data.id,
            title = data.title,
            description = data.description,
            priority = data.priority
        )

        override fun mapFrom(domain: ToDo): ToDoEntity = ToDoEntity(
            id = domain.id,
            title = domain.title,
            description = domain.description,
            priority = domain.priority
        )
    }
}