package com.eray.horoscopeapp.model

import com.google.firebase.firestore.QuerySnapshot

data class Horoscope(val id: Long?, val name: String?, val day: String? = null, val imageUrl: String?) {
    object Mapper {
        fun toList(qs: QuerySnapshot?) =
            qs?.documents?.map {
                Horoscope(
                    it.get("id") as? Long,
                    it.get("isim") as? String,
                    it.get("day") as? String,
                    it.get("imageUrl") as? String
                )
            }
    }
}