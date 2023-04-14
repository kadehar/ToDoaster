package com.example.todoaster.fragments

import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.todoaster.R
import com.example.todoaster.data.models.Priority

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            when (position) {
                0 -> setColorTo(parent = parent, context = application, color = R.color.red)
                1 -> setColorTo(parent = parent, context = application, color = R.color.yellow)
                2 -> setColorTo(parent = parent, context = application, color = R.color.green)
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }

    fun verify(title: String, desc: String): Boolean =
        !(TextUtils.isEmpty(title) || TextUtils.isEmpty(desc))


    fun toPriority(value: String): Priority = Priority.valueOf(value)

    fun positionOf(priority: Priority) = when (priority) {
        Priority.High -> 0
        Priority.Medium -> 1
        Priority.Low -> 2
    }

    private fun setColorTo(
        parent: AdapterView<*>?,
        context: Context,
        @ColorRes color: Int
    ) =
        (parent?.getChildAt(0) as TextView)
            .setTextColor(ContextCompat.getColor(context, color))
}