package com.eray.horoscopeapp.model

import android.os.Parcelable
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class Horoscope(
    val id: Long?,
    val name: String?,
    val day: String? = null,
    val imageUrl: String?,
    val businessOverall: String? = null,
    val description: String? = null,
    val loveOverall: String? = null,
    val luckyNumbers: String? = null
) : Parcelable {
    object Mapper {
        fun toList(qs: QuerySnapshot?) =
            qs?.documents?.map {
                Horoscope(
                    id = it.get("id") as? Long,
                    name = it.get("isim") as? String,
                    day = it.get("day") as? String,
                    imageUrl = it.get("imageUrl") as? String,
                    businessOverall = it.get("businessOverall") as? String,
                    description = it.get("description") as? String,
                    loveOverall = it.get("loveOverall") as? String,
                    luckyNumbers = it.get("luckyNumbers") as? String
                )
            }
    }
}