package com.example.tat.utils.extensions

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

fun View.applyColor(color: Int) {
    val backgroundColor = ContextCompat.getColor(context, color)
    (this as TextView).setTextColor(backgroundColor)
}