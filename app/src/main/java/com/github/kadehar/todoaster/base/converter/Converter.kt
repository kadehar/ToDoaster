package com.github.kadehar.todoaster.base.converter

import androidx.room.TypeConverter
import com.github.kadehar.todoaster.base.priority.Priority

interface Converter<T> {
    fun from(t: T): String
    fun to(value: String): T

    class Base : Converter<Priority> {
        @TypeConverter
        override fun from(t: Priority): String = t.name

        @TypeConverter
        override fun to(value: String): Priority = Priority.valueOf(value)
    }
}