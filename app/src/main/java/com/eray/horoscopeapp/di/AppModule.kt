package com.eray.horoscopeapp.di

import android.content.Context
import com.eray.horoscopeapp.data.pref.Prefs
import com.eray.horoscopeapp.data.pref.PrefsImpl
import com.eray.horoscopeapp.data.repository.HoroscopeRepository
import com.eray.horoscopeapp.data.repository.HoroscopeRepositoryImpl
import com.eray.horoscopeapp.util.Constants.HOROSCOPES
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
    fun provideHoroscopeRef(
        db: FirebaseFirestore
    ): CollectionReference = db.collection(HOROSCOPES)

    @Singleton
    @Provides
    fun provideHoroscopeRepository(
        horoscopeRef: CollectionReference
    ): HoroscopeRepository =
        HoroscopeRepositoryImpl(horoscopeRef)

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext appContext: Context): Prefs = PrefsImpl(appContext)
}