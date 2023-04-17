package com.github.kadehar.todoaster.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDAO {

    @Query("SELECT * FROM ${ToDoEntity.TABLE_NAME} ORDER BY id ASC")
    fun all(): LiveData<List<ToDoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(data: ToDoEntity)

    @Update
    suspend fun edit(data: ToDoEntity)

    @Delete
    suspend fun delete(data: ToDoEntity)

    @Query("DELETE FROM ${ToDoEntity.TABLE_NAME}")
    suspend fun deleteAll()

    @Query("SELECT * FROM ${ToDoEntity.TABLE_NAME} WHERE title LIKE :query OR description LIKE :query")
    fun search(query: String): LiveData<List<ToDoEntity>>

    @Query("SELECT * FROM ${ToDoEntity.TABLE_NAME} ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<ToDoEntity>>

    @Query("SELECT * FROM ${ToDoEntity.TABLE_NAME} ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<ToDoEntity>>
}