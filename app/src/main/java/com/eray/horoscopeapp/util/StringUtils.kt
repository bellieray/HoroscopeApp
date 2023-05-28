package com.eray.horoscopeapp.util

object StringUtils {
    fun checkInformation(vararg texts: String): Boolean {
        return texts.all { it.isBlank().not() && it.isNotEmpty() }
    }

    fun calculateNameNumber(name: String, language: String): String {
        val letterValues = when (language) {
            "tr" -> {
                mapOf(
                    'A' to 1,
                    'B' to 2,
                    'C' to 3,
                    'Ç' to 4,
                    'D' to 5,
                    'E' to 6,
                    'F' to 7,
                    'G' to 8,
                    'Ğ' to 9,
                    'H' to 1,
                    'I' to 2,
                    'İ' to 3,
                    'J' to 4,
                    'K' to 5,
                    'L' to 6,
                    'M' to 7,
                    'N' to 8,
                    'O' to 9,
                    'Ö' to 1,
                    'P' to 2,
                    'R' to 3,
                    'S' to 4,
                    'Ş' to 5,
                    'T' to 6,
                    'U' to 7,
                    'Ü' to 8,
                    'V' to 9,
                    'Y' to 1,
                    'Z' to 2
                )
            }
            else -> {
                mapOf(
                    'A' to 1,
                    'B' to 2,
                    'C' to 3,
                    'D' to 4,
                    'E' to 5,
                    'F' to 6,
                    'G' to 7,
                    'H' to 8,
                    'I' to 9,
                    'J' to 1,
                    'K' to 2,
                    'L' to 3,
                    'M' to 4,
                    'N' to 5,
                    'O' to 6,
                    'P' to 7,
                    'Q' to 8,
                    'R' to 9,
                    'S' to 1,
                    'T' to 2,
                    'U' to 3,
                    'V' to 4,
                    'W' to 5,
                    'X' to 6,
                    'Y' to 7,
                    'Z' to 8
                )
            }
        }
        val nameValue = name.uppercase().map { letterValues[it] ?: 0 }.sum()
        return reduceToSingleDigit(nameValue).toString()
    }

    private fun reduceToSingleDigit(number: Int): Int {
        return if (number < 10 || number == 11 || number == 22 || number == 33) {
            number
        } else {
            val digits = number.toString().map { it.toString().toInt() }
            reduceToSingleDigit(digits.sum())
        }
    }

    fun calculateNameMatch(firstName: String, secondName: String): String {
        val name = firstName + secondName
        val sameCharCount = mutableMapOf<Char, Int>()
        name.forEach {
            val count = sameCharCount.getOrElse(it) { 0 }
            sameCharCount[it] = count + 1
        }
        val charCountList = mutableListOf<Int>()
        sameCharCount.forEach { charCountList.add(it.value) }
        return sumOf(charCountList)
    }

    private fun sumOf(charCount: MutableList<Int>): String {
        val newCharCount = mutableListOf<Int>()
        charCount.forEach {
            if (it > 9) {
                newCharCount.add(it / 10)
                newCharCount.add(it % 10)
            } else {
                newCharCount.add(it)
            }
        }
        return if (newCharCount.size > 2) {
            val sameCharCount = mutableListOf<Int>()
            for (i in 1..newCharCount.size / 2) {
                sameCharCount.add(newCharCount[0] + newCharCount[newCharCount.size - 1])
                newCharCount.removeAt(0)
                newCharCount.removeAt(newCharCount.size - 1)
            }
            if (newCharCount.size > 0) sameCharCount.add(newCharCount[0])
            sumOf(sameCharCount)
        } else {
            if (newCharCount.size == 1) newCharCount[0].toString()
            else newCharCount[0].toString() + newCharCount[1].toString()
        }
    }
}