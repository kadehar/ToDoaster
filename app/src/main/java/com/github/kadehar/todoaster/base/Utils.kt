package com.github.kadehar.todoaster.base

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = activity.currentFocus
    currentFocus?.let {
        imm.hideSoftInputFromWindow(
            currentFocus.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}