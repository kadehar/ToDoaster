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

    @Query("DELETE FROM ${ToDoData.TABLE_NAME}")
    suspend fun deleteAll()

    @Query("SELECT * FROM ${ToDoData.TABLE_NAME} WHERE title LIKE :query")
    fun search(query: String): LiveData<List<ToDoData>>

    @Query("SELECT * FROM ${ToDoData.TABLE_NAME} ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<ToDoData>>

    @Query("SELECT * FROM ${ToDoData.TABLE_NAME} ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<ToDoData>>
}