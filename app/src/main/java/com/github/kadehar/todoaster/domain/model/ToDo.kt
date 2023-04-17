package com.github.kadehar.todoaster.domain.model

import android.os.Parcelable
import com.github.kadehar.todoaster.base.priority.Priority
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToDo(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority
) : Parcelable