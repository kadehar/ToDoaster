package com.example.todoaster.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoaster.data.models.ToDoData.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class ToDoData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "priority") val priority: Priority,
    @ColumnInfo(name = "description") val description: String
): Parcelable {
    companion object {
        const val TABLE_NAME = "todo_table"
    }
}