package com.eray.horoscopeapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonalDetail(val name: String, val gender: String, val birthTime: String) : Parcelable