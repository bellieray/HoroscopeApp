package com.eray.horoscopeapp.data.repository

import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.ui.match_detail.HoroscopeMatchItem
import com.eray.horoscopeapp.util.ChineseHoroscopeReference
import com.eray.horoscopeapp.util.HoroscopeReference
import com.eray.horoscopeapp.util.MatchingHoroscopeReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HoroscopeRepositoryImpl @Inject constructor(
    @HoroscopeReference val horoscopeRef: CollectionReference,
    @MatchingHoroscopeReference val matchingHoroscopeReference: CollectionReference,
    @ChineseHoroscopeReference val chineseHoroscopeReference: CollectionReference
) :
    HoroscopeRepository {


    override suspend fun getHoroscopes(): Result<List<Horoscope>?> =
        suspendCoroutine { cont ->
            var isResumed = false // Flag to track if the coroutine has been resumed

            horoscopeRef.orderBy("id", Query.Direction.ASCENDING)
                .addSnapshotListener { value, error ->
                    if (!isResumed) { // Check if the coroutine has already been resumed
                        if (error == null)
                            cont.resume(Result.Success(Horoscope.Mapper.toList(value)))
                        else
                            cont.resume(Result.Failed(error))

                        isResumed = true // Set the flag to true after resuming the coroutine
                    }
                }
        }

    override suspend fun getHoroscopeById(id: Long): Result<List<Horoscope>?> =
        suspendCoroutine { cont ->
            horoscopeRef.orderBy("id", Query.Direction.ASCENDING).whereEqualTo("id", id)
                .addSnapshotListener { value, error ->
                    if (error == null)
                        cont.resume(Result.Success(Horoscope.Mapper.toList(value)))
                    else
                        cont.resume(Result.Failed(error))
                }
        }

    override suspend fun getMatchHoroscopeById(id: String): Result<List<HoroscopeMatchItem>?> =
        suspendCoroutine { cont ->
            matchingHoroscopeReference
                .whereEqualTo("id", id)
                .addSnapshotListener { value, error ->
                    if (error == null && value?.isEmpty?.not() == true)
                        cont.resume(
                            Result.Success(
                                HoroscopeMatchItem.toHoroscopeMatchItemList(
                                    value
                                )
                            )
                        )
                    else
                        cont.resume(Result.Failed(IOException()))
                }
        }

    override suspend fun getChineseHoroscopes(): Result<List<Horoscope>?> =
        suspendCoroutine { cont ->
            var isResumed = false // Flag to track if the coroutine has been resumed
            chineseHoroscopeReference.orderBy("id", Query.Direction.ASCENDING)
                .addSnapshotListener { value, error ->
                    if (!isResumed) { // Check if the coroutine has already been resumed
                        if (error == null)
                            cont.resume(Result.Success(Horoscope.Mapper.toList(value)))
                        else
                            cont.resume(Result.Failed(error))

                        isResumed = true // Set the flag to true after resuming the coroutine
                    }
                }
        }
}