package com.raassh.dicodingcomposefinal.ui.utils

import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Double.withNumberingFormat(): String {
    return NumberFormat.getNumberInstance().format(this)
}

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    val date = format.parse(this) as Date
    return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date)
}