package com.eray.horoscopeapp.model

import com.google.firebase.firestore.QuerySnapshot

data class Tarot(
    val id: String?,
    val name: String?,
    val description: String?,
    val imageUrl: String?
) {
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