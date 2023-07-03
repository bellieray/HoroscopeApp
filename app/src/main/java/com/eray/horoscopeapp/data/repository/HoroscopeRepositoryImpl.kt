package com.eray.horoscopeapp.data.repository

import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.model.Tarot
import com.eray.horoscopeapp.ui.match_detail.HoroscopeMatchItem
import com.eray.horoscopeapp.util.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HoroscopeRepositoryImpl @Inject constructor(
    @HoroscopeReference val horoscopeRef: CollectionReference,
    @MatchingHoroscopeReference val matchingHoroscopeReference: CollectionReference,
    @ChineseHoroscopeReference val chineseHoroscopeReference: CollectionReference,
    @TarotReferenceTr val tarotReferenceTr: CollectionReference,
    @HoroscopeReferenceTR val horoscopeReferenceTR: CollectionReference,
    @TarotReferenceEn val tarotReferenceEn: CollectionReference,
    @ChineseHoroscopeReferenceTr val chineseHoroscopeReferenceTr: CollectionReference,
    @MatchingHoroscopeReferenceTr val matchingHoroscopeReferenceTr: CollectionReference
) :
    HoroscopeRepository {


    override suspend fun getHoroscopes(isEnglish: Boolean): Result<List<Horoscope>?> =
        suspendCoroutine { cont ->
            val ref = if (isEnglish) horoscopeRef else horoscopeReferenceTR
            var isResumed = false // Flag to track if the coroutine has been resumed

            ref.orderBy("id", Query.Direction.ASCENDING)
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

    override suspend fun getHoroscopeById(id: Long, isEnglish: Boolean): Result<List<Horoscope>?> =
        suspendCoroutine { cont ->
            val ref = if (isEnglish) horoscopeRef else horoscopeReferenceTR
            ref.orderBy("id", Query.Direction.ASCENDING).whereEqualTo("id", id)
                .addSnapshotListener { value, error ->
                    if (error == null)
                        cont.resume(Result.Success(Horoscope.Mapper.toList(value)))
                    else
                        cont.resume(Result.Failed(error))
                }
        }

    override suspend fun getMatchHoroscopeById(
        id: String,
        isEnglish: Boolean
    ): Result<List<HoroscopeMatchItem>?> =
        suspendCoroutine { cont ->
            val ref = if (isEnglish) matchingHoroscopeReference else matchingHoroscopeReferenceTr
            var isResumed = false // Flag to track if the coroutine has been resumed
            ref
                .whereEqualTo("id", id)
                .addSnapshotListener { value, error ->
                    if (!isResumed) { // Check if the coroutine has already been resumed
                        if (error == null)
                            cont.resume(
                                Result.Success(
                                    HoroscopeMatchItem.toHoroscopeMatchItemList(
                                        value
                                    )
                                )
                            )
                        else
                            cont.resume(Result.Failed(IOException()))
                        isResumed = true // Set the flag to true after resuming the coroutine
                    }
                }
        }

    override suspend fun getChineseHoroscopes(isEnglish: Boolean): Result<List<Horoscope>?> =
        suspendCoroutine { cont ->
            val ref = if (isEnglish) chineseHoroscopeReference else chineseHoroscopeReferenceTr
            var isResumed = false // Flag to track if the coroutine has been resumed
            ref.orderBy("id", Query.Direction.ASCENDING)
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

    override suspend fun getTarots(isEnglish: Boolean): Result<List<Tarot>?> =
        suspendCoroutine { cont ->
            val ref = if (isEnglish) tarotReferenceEn else tarotReferenceTr
            var isResumed = false // Flag to track if the coroutine has been resumed
            ref.orderBy("id", Query.Direction.ASCENDING)
                .addSnapshotListener { value, error ->
                    if (!isResumed) { // Check if the coroutine has already been resumed
                        if (error == null)
                            cont.resume(Result.Success(Tarot.Mapper.toTarot(value)))
                        else
                            cont.resume(Result.Failed(error))

                        isResumed = true // Set the flag to true after resuming the coroutine
                    }
                }
        }
}