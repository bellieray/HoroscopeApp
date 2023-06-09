package com.eray.horoscopeapp.data.repository.fortune

import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.ui.fortune.NameFortuneItem
import com.eray.horoscopeapp.util.NameFortuneReference
import com.google.firebase.firestore.CollectionReference
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FortuneRepositoryImpl @Inject constructor(
    @NameFortuneReference val nameFortuneReference: CollectionReference,
) :
    FortuneRepository {

    override suspend fun getNameFortuneById(id: String): Result<List<NameFortuneItem>?> =
        suspendCoroutine { cont ->
            nameFortuneReference
                .whereEqualTo("id", id)
                .addSnapshotListener { value, error ->
                    if (error == null && value?.isEmpty?.not() == true)
                        cont.resume(
                            Result.Success(
                                NameFortuneItem.toNameFortuneItemList(
                                    value
                                )
                            )
                        )
                    else
                        cont.resume(Result.Failed(IOException()))
                }
        }
}