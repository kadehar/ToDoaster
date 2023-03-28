package com.example.todoaster.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoaster.data.models.ToDoData

@Dao
interface ToDoDAO {

    @Query("SELECT * FROM ${ToDoData.TABLE_NAME} ORDER BY id ASC")
    fun findAll(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNew(toDoData: ToDoData)

    @Update
    suspend fun update(toDoData: ToDoData)

    @Delete
    suspend fun delete(toDoData: ToDoData)
}