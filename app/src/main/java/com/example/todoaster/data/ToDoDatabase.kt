package com.example.todoaster.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoaster.data.models.ToDoData

@Database(
    entities = [ToDoData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PriorityConverter::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDAO

    companion object {

        private const val DB_NAME = "todo_db"

        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = ToDoDatabase::class.java,
                    name = DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}