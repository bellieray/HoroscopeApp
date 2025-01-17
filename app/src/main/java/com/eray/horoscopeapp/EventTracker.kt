package com.eray.horoscopeapp

import com.appodeal.ads.ext.toBundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventTracker @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics,
) {
    fun logEvent(eventName: String, params: Map<String, Any> = emptyMap()) {
        firebaseAnalytics.logEvent(eventName, params.toBundle())
    }
}