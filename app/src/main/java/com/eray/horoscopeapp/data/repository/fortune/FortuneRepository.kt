package com.eray.horoscopeapp.data.repository.fortune

import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.ui.fortune.NameFortuneItem

interface FortuneRepository {
    suspend fun getNameFortuneById(id: String, isEnglish: Boolean): Result<List<NameFortuneItem>?>
}