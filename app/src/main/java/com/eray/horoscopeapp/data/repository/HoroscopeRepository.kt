package com.eray.horoscopeapp.data.repository

import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.model.Result

interface HoroscopeRepository {
    suspend fun getHoroscopes(): Result<List<Horoscope>?>
    suspend fun getHoroscopeById(id: Long) : Result<List<Horoscope>?>
}