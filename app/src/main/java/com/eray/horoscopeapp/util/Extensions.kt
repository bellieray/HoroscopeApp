package com.eray.horoscopeapp.util

import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.eray.horoscopeapp.R
import java.text.SimpleDateFormat
import java.util.*

private const val PATTERN = "MMM/dd"
fun NavController.navigateWithPushAnimation(directions: NavDirections) {
    val navOptions = NavOptions
        .Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()
    navigate(directions, navOptions)
}

fun String.checkHoroscope(): Pair<String, Int> {
    return when (this.toDate()) {
        in "03/21".toDate().."04/19".toDate() -> "Aries" to R.drawable.ic_aries
        in "04/20".toDate().."05/20".toDate() -> "Taurus" to R.drawable.ic_taurus
        in "05/21".toDate().."06/20".toDate() -> "Gemini" to R.drawable.ic_gemini
        in "07/23".toDate().."08/22".toDate() -> "Leo" to R.drawable.ic_leo
        in "06/21".toDate().."07/22".toDate() -> "Cancer" to R.drawable.ic_cancer
        in "08/23".toDate().."09/22".toDate() -> "Virgo" to R.drawable.ic_virgo
        in "09/23".toDate().."10/22".toDate() -> "Libra" to R.drawable.ic_libra
        in "10/23".toDate().."11/21".toDate() -> "Scorpio" to R.drawable.ic_scorpio
        in "11/22".toDate().."12/21".toDate() -> "Sagittarius" to R.drawable.ic_sagittarius
        in "12/22".toDate().."01/19".toDate() -> "Capricorn" to R.drawable.ic_capricorn
        in "01/20".toDate().."02/18".toDate() -> "Aquarius" to R.drawable.ic_aquarius
        in "02/19".toDate().."03/20".toDate() -> "Pisces" to R.drawable.ic_pisces
        else -> "" to 0
    }
}

fun String.toDate(): Long {
    val x = SimpleDateFormat("dd/mm", Locale.getDefault()).parse(this).time
    return x
}


fun ImageView.setBgWithId(id: Long?) {
    val image = when (id) {
        1L -> R.drawable.ic_leo
        2L -> R.drawable.ic_scorpio
        3L -> R.drawable.ic_pisces
        4L -> R.drawable.ic_virgo
        5L -> R.drawable.ic_taurus
        6L -> R.drawable.ic_gemini
        7L -> R.drawable.ic_aries
        8L -> R.drawable.ic_aquarius
        9L -> R.drawable.ic_capricorn
        10L -> R.drawable.ic_libra
        11L -> R.drawable.ic_sagittarius
        12L -> R.drawable.ic_cancer
        else -> 0
    }
    this.setImageResource(image)
}
