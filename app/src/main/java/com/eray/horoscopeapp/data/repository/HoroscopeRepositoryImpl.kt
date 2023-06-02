package com.eray.horoscopeapp.data.repository

import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.ui.match_detail.HoroscopeMatchItem
import com.eray.horoscopeapp.util.HoroscopeReference
import com.eray.horoscopeapp.util.MatchingHoroscopeReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HoroscopeRepositoryImpl @Inject constructor(
    @HoroscopeReference val horoscopeRef: CollectionReference,
    @MatchingHoroscopeReference val matchingHoroscopeReference: CollectionReference
) :
    HoroscopeRepository {


    override suspend fun getHoroscopes(): Result<List<Horoscope>?> =
        suspendCoroutine { cont ->
                horoscopeRef.orderBy("id", Query.Direction.ASCENDING)
                    .addSnapshotListener { value, error ->
                        if (error == null)
                            cont.resume(Result.Success(Horoscope.Mapper.toList(value)))
                        else
                            cont.resume(Result.Failed(error.localizedMessage.toString()))
                    }
        }

    override suspend fun getHoroscopeById(id: Long): Result<List<Horoscope>?> =
        suspendCoroutine { cont ->
                horoscopeRef.orderBy("id", Query.Direction.ASCENDING).whereEqualTo("id", id)
                    .addSnapshotListener { value, error ->
                        if (error == null)
                            cont.resume(Result.Success(Horoscope.Mapper.toList(value)))
                        else
                            cont.resume(Result.Failed(error.localizedMessage.toString()))
                    }
        }

    override suspend fun getMatchHoroscopeById(id: String): Result<List<HoroscopeMatchItem>?> =
        suspendCoroutine { cont ->
                matchingHoroscopeReference
                    .whereEqualTo("id", id)
                    .addSnapshotListener { value, error ->
                        if (error == null)
                            cont.resume(
                                Result.Success(
                                    HoroscopeMatchItem.toHoroscopeMatchItemList(
                                        value
                                    )
                                )
                            )
                        else
                            cont.resume(Result.Failed(error.localizedMessage.toString()))
            }
        }
}