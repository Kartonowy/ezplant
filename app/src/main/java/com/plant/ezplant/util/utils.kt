package com.plant.ezplant.util

fun Boolean.toInt() = if (this) 1 else 0

fun Int.toBoolean() = this.toInt() != 0