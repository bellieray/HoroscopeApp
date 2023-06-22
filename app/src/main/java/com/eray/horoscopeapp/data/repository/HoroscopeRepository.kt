package com.eray.horoscopeapp.data.repository

import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.model.Tarot
import com.eray.horoscopeapp.ui.match_detail.HoroscopeMatchItem

interface HoroscopeRepository {
    suspend fun getHoroscopes(): Result<List<Horoscope>?>
    suspend fun getHoroscopeById(id: Long) : Result<List<Horoscope>?>
    suspend fun getMatchHoroscopeById(id: String) : Result<List<HoroscopeMatchItem>?>
    suspend fun getChineseHoroscopes() : Result<List<Horoscope>?>
    suspend fun getTarots() : Result<List<Tarot>?>

}