package com.eray.horoscopeapp.di

import android.content.Context
import com.eray.horoscopeapp.App
import com.eray.horoscopeapp.data.pref.Prefs
import com.eray.horoscopeapp.data.pref.PrefsImpl
import com.eray.horoscopeapp.data.repository.HoroscopeRepository
import com.eray.horoscopeapp.data.repository.HoroscopeRepositoryImpl
import com.eray.horoscopeapp.data.repository.fortune.FortuneRepository
import com.eray.horoscopeapp.data.repository.fortune.FortuneRepositoryImpl
import com.eray.horoscopeapp.util.*
import com.eray.horoscopeapp.util.Constants.CHINESE_HOROSCOPE_EN
import com.eray.horoscopeapp.util.Constants.CHINESE_HOROSCOPE_TR
import com.eray.horoscopeapp.util.Constants.HOROSCOPES
import com.eray.horoscopeapp.util.Constants.HOROSCOPES_TR
import com.eray.horoscopeapp.util.Constants.MATCHING_HOROSCOPES_EN
import com.eray.horoscopeapp.util.Constants.MATCHING_HOROSCOPES_TR
import com.eray.horoscopeapp.util.Constants.NAME_FORTUNE_EN
import com.eray.horoscopeapp.util.Constants.NAME_FORTUNE_TR
import com.eray.horoscopeapp.util.Constants.TAROT_EN
import com.eray.horoscopeapp.util.Constants.TAROT_TR
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFirebase(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    @HoroscopeReference
    fun provideHoroscopeRef(
        db: FirebaseFirestore
    ): CollectionReference = db.collection(HOROSCOPES)

    @Singleton
    @Provides
    @HoroscopeReferenceTR
    fun provideHoroscopeRefTR(
        db: FirebaseFirestore
    ): CollectionReference = db.collection(HOROSCOPES_TR)

    @Singleton
    @Provides
    @MatchingHoroscopeReference
    fun provideMatchingHoroscopeRef(
        db: FirebaseFirestore
    ): CollectionReference = db.collection(MATCHING_HOROSCOPES_EN)


    @Singleton
    @Provides
    @MatchingHoroscopeReferenceTr
    fun provideMatchingHoroscopeRefTr(
        db: FirebaseFirestore
    ): CollectionReference = db.collection(MATCHING_HOROSCOPES_TR)

    @Singleton
    @Provides
    @ChineseHoroscopeReference
    fun provideChineseHoroscopeRef(
        db: FirebaseFirestore
    ): CollectionReference = db.collection(CHINESE_HOROSCOPE_EN)

    @Singleton
    @Provides
    @ChineseHoroscopeReferenceTr
    fun provideChineseHoroscopeRefTr(
        db: FirebaseFirestore
    ): CollectionReference = db.collection(CHINESE_HOROSCOPE_TR)

    @Singleton
    @Provides
    @NameFortuneReference
    fun provideNameFortuneRef(
        db: FirebaseFirestore
    ): CollectionReference = db.collection(NAME_FORTUNE_EN)

    @Singleton
    @Provides
    @NameFortuneReferenceTr
    fun provideNameFortuneRefTr(
        db: FirebaseFirestore
    ): CollectionReference = db.collection(NAME_FORTUNE_TR)

    @Singleton
    @Provides
    @TarotReferenceTr
    fun provideTarotRefTr(
        db: FirebaseFirestore
    ): CollectionReference = db.collection(TAROT_TR)

    @Singleton
    @Provides
    @TarotReferenceEn
    fun provideTarotRefEn(
        db: FirebaseFirestore
    ): CollectionReference = db.collection(TAROT_EN)

    @Singleton
    @Provides
    fun provideHoroscopeRepository(
        @HoroscopeReference horoscopeReference: CollectionReference,
        @MatchingHoroscopeReference matchingHoroscopeReference: CollectionReference,
        @ChineseHoroscopeReference chineseHoroscopeReference: CollectionReference,
        @TarotReferenceTr tarotReference: CollectionReference,
        @HoroscopeReferenceTR horoscopeReferenceTR: CollectionReference,
        @TarotReferenceEn tarotReferenceEn: CollectionReference,
        @ChineseHoroscopeReferenceTr chineseHoroscopeReferenceTr: CollectionReference,
        @MatchingHoroscopeReferenceTr matchingHoroscopeReferenceTr: CollectionReference
    ): HoroscopeRepository =
        HoroscopeRepositoryImpl(
            horoscopeReference,
            matchingHoroscopeReference,
            chineseHoroscopeReference,
            tarotReference,
            horoscopeReferenceTR,
            tarotReferenceEn,
            chineseHoroscopeReferenceTr,
            matchingHoroscopeReferenceTr
        )

    @Singleton
    @Provides
    fun provideFortuneRepository(
        @NameFortuneReference nameFortuneReference: CollectionReference,
        @NameFortuneReferenceTr nameFortuneReferenceTr: CollectionReference
    ): FortuneRepository = FortuneRepositoryImpl(nameFortuneReference, nameFortuneReferenceTr)

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext appContext: Context): Prefs = PrefsImpl(appContext)


    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext context: Context): App {
        return context as App
    }
}