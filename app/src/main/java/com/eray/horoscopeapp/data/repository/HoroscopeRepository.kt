package com.eray.horoscopeapp.data.repository

import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.model.Tarot
import com.eray.horoscopeapp.ui.match_detail.HoroscopeMatchItem

interface HoroscopeRepository {
    suspend fun getHoroscopes(isEnglish: Boolean): Result<List<Horoscope>?>
    suspend fun getHoroscopeById(id: Long, isEnglish: Boolean) : Result<List<Horoscope>?>
    suspend fun getMatchHoroscopeById(id: String, isEnglish: Boolean) : Result<List<HoroscopeMatchItem>?>
    suspend fun getChineseHoroscopes(isEnglish: Boolean) : Result<List<Horoscope>?>
    suspend fun getTarots(isEnglish: Boolean) : Result<List<Tarot>?>

}