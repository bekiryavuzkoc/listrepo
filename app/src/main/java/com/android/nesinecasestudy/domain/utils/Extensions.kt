package com.android.nesinecasestudy.domain.utils

fun String?.emptyString(): String {
    return if (this.isNullOrBlank()) "" else this
}