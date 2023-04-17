package com.github.kadehar.todoaster.base.ext

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter

fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}

fun TextView.backgroundColor(@ColorRes color: Int) {
    setBackgroundColor(ContextCompat.getColor(this.context, color))
}