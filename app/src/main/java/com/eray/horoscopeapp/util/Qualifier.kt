package com.eray.horoscopeapp.util

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HoroscopeReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MatchingHoroscopeReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NameFortuneReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChineseHoroscopeReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TarotReference