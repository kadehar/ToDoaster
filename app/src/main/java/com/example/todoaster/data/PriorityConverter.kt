package com.example.todoaster.data

import androidx.room.TypeConverter
import com.example.todoaster.data.models.Priority

class PriorityConverter {

    @TypeConverter
    fun from(priority: Priority): String = priority.name

    @TypeConverter
    fun to(priority: String): Priority = Priority.valueOf(priority)
}