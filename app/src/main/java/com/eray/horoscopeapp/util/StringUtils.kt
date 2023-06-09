package com.eray.horoscopeapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

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

    private val signWithDegrees = mapOf(
        30 to 7, 60 to 5, 90 to 6, 120 to 12, 150 to 1, 180 to 4,
        210 to 10, 240 to 2, 270 to 11, 300 to 9, 330 to 8, 360 to 3
    )

    @SuppressLint("SimpleDateFormat")
    fun calculateMoonSign(birthDate: String, birthTime: String): Int {
        val format = SimpleDateFormat("dd / mm / yyyy HH : mm")
        val date = format.parse("$birthDate $birthTime")
        val calendar = Calendar.getInstance()
        date?.let {
            calendar.time = it
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val a = (14 - month) / 12
        val y = year + 4800 - a
        val m = month + 12 * a - 3
        val jd = day + ((153 * m + 2) / 5) + (365 * y) + (y / 4) - 32083
        val daysSinceJ2000 = jd - 2451550.1
        val moonLongitude = (218.316 + 13.176396 * daysSinceJ2000) % 360
        var moonSign: Int? = null
        for(i in signWithDegrees.entries){
            if (moonLongitude < i.key) {
                moonSign = i.value
                break
            }
        }
        return moonSign ?: signWithDegrees.values.last()
    }

    val horoscopesWithId = mapOf(
        "Aries" to 7, "Taurus" to 5, "Gemini" to 6, "Cancer" to 12, "Leo" to 1, "Virgo" to 4,
        "Libra" to 10, "Scorpio" to 2, "Sagittarius" to 11, "Capricorn" to 9, "Aquarius" to 8, "Pisces" to 3
    )
    private val zodiacSigns = listOf(
        "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
        "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"
    )
    val cutoffDates = listOf(
        "03-21", "04-20", "05-21", "06-21", "07-23", "08-23",
        "09-23", "10-23", "11-22", "12-22", "01-20", "02-19"
    )

    @SuppressLint("SimpleDateFormat")
    fun calculateSunSign(birthDate: String, birthTime: String): Int? {
        val format = SimpleDateFormat("dd / mm / yyyy HH : mm")
        val dateTime = format.parse("$birthDate $birthTime")
        val calendar = Calendar.getInstance()
        dateTime?.let {
            calendar.time = it
        }
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val cutoffDate = "${cutoffDates[month - 1]}-${calendar.get(Calendar.YEAR)}"
        val cutoffFormat = SimpleDateFormat("MM-dd-yyyy")
        val cutoff = cutoffFormat.parse(cutoffDate)
        return if (dateTime.before(cutoff)) {
            // Use the zodiac sign of the previous month
            val previousMonth = if (month == 1) 12 else month - 1
            horoscopesWithId[zodiacSigns[previousMonth - 1]]
        } else {
            // Use the zodiac sign of the current month
            horoscopesWithId[zodiacSigns[month - 1]]
        }
    }
}