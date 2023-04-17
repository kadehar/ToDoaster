package com.github.kadehar.todoaster.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.kadehar.todoaster.base.priority.Priority
import com.github.kadehar.todoaster.data.local.ToDoEntity.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "priority") val priority: Priority,
    @ColumnInfo(name = "description") val description: String
) : Parcelable {
    companion object {
        const val TABLE_NAME = "todo"
    }
}