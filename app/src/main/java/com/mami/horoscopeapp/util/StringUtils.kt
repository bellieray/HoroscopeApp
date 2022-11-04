package com.mami.horoscopeapp.util

object StringUtils {
    fun checkInformation(vararg texts: String): Boolean {
        return texts.all { it.isBlank().not() && it.isNotEmpty() }
    }
}