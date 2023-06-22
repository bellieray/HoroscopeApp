package com.eray.horoscopeapp.model

import android.os.Parcelable
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tarot(
    val id: String?,
    val name: String?,
    val description: String?,
    val imageUrl: String?,
    var isSelected: Boolean = false
) : Parcelable{
    companion object Mapper {
        fun toTarot(qs: QuerySnapshot?): List<Tarot>? {
            return qs?.documents?.map {
                Tarot(
                    id = it.get("id") as? String,
                    name = it.get("name") as? String,
                    description = it.get("description") as? String,
                    imageUrl = it.get("imageUrl") as? String
                )
            }
        }
    }
}