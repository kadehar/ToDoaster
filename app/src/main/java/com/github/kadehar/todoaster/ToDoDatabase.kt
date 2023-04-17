package com.github.kadehar.todoaster

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.kadehar.todoaster.base.converter.Converter
import com.github.kadehar.todoaster.data.local.ToDoDAO
import com.github.kadehar.todoaster.data.local.ToDoEntity

@Database(
    entities = [ToDoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter.Base::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun dao(): ToDoDAO

    companion object {
        const val DB_NAME = "todo_db"
    }
}