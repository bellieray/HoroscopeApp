package com.eray.horoscopeapp.model

import com.google.firebase.firestore.QuerySnapshot

data class Horoscope(
    val id: Long?,
    val name: String?,
    val day: String? = null,
    val imageUrl: String?,
    val businessOverall: String? = null,
    val description: String? = null,
    val loveOverall: String? = null,
    val luckyNumbers: String? = null
) {
    object Mapper {
        fun toList(qs: QuerySnapshot?) =
            qs?.documents?.map {
                Horoscope(
                    it.get("id") as? Long,
                    it.get("isim") as? String,
                    it.get("day") as? String,
                    it.get("imageUrl") as? String,
                    it.get("businessOverall") as? String,
                    it.get("description") as? String,
                    it.get("loveOverall") as? String,
                    it.get("luckyNumbers") as? String
                )
            }
    }
}