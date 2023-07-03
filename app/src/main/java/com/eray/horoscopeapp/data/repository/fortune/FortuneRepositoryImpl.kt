package com.eray.horoscopeapp.data.repository.fortune

import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.ui.fortune.NameFortuneItem
import com.eray.horoscopeapp.util.NameFortuneReference
import com.eray.horoscopeapp.util.NameFortuneReferenceTr
import com.google.firebase.firestore.CollectionReference
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FortuneRepositoryImpl @Inject constructor(
    @NameFortuneReference val nameFortuneReference: CollectionReference,
    @NameFortuneReferenceTr val nameFortuneReferenceTr: CollectionReference
) :
    FortuneRepository {

    override suspend fun getNameFortuneById(id: String, isEnglish: Boolean): Result<List<NameFortuneItem>?> =
        suspendCoroutine { cont ->
            val ref = if (isEnglish) nameFortuneReference else nameFortuneReferenceTr
            ref
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