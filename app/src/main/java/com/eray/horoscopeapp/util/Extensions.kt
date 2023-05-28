package com.eray.horoscopeapp.util

import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.ui.profile.adapter.UserHoroscopeProperties
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

fun String.getHoroscopeIdFromDate(): Int {
    return when {
        (this.substring(5, 7).toInt() == 3 && this.substring(0, 2)
            .toInt() >= 21) || (this.substring(5, 7).toInt() == 4 && this.substring(0, 2)
            .toInt() <= 19) -> 7
        (this.substring(5, 7).toInt() == 4 && this.substring(0, 2)
            .toInt() >= 20) || (this.substring(5, 7).toInt() == 5 && this.substring(0, 2)
            .toInt() <= 20) -> 5
        (this.substring(5, 7).toInt() == 5 && this.substring(0, 2)
            .toInt() >= 21) || (this.substring(5, 7).toInt() == 6 && this.substring(0, 2)
            .toInt() <= 20) -> 6
        (this.substring(5, 7).toInt() == 6 && this.substring(0, 2)
            .toInt() >= 21) || (this.substring(5, 7).toInt() == 7 && this.substring(0, 2)
            .toInt() <= 22) -> 12
        (this.substring(5, 7).toInt() == 7 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 8 && this.substring(0, 2)
            .toInt() <= 22) -> 1
        (this.substring(5, 7).toInt() == 8 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 9 && this.substring(0, 2)
            .toInt() <= 22) -> 4
        (this.substring(5, 7).toInt() == 9 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 10 && this.substring(0, 2)
            .toInt() <= 22) -> 10
        (this.substring(5, 7).toInt() == 10 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 11 && this.substring(0, 2)
            .toInt() <= 21) -> 2
        (this.substring(5, 7).toInt() == 11 && this.substring(0, 2)
            .toInt() >= 22) || (this.substring(5, 7).toInt() == 12 && this.substring(0, 2)
            .toInt() <= 21) -> 11
        (this.substring(5, 7).toInt() == 12 && this.substring(0, 2)
            .toInt() >= 22) || (this.substring(5, 7).toInt() == 1 && this.substring(0, 2)
            .toInt() <= 19) -> 9
        (this.substring(5, 7).toInt() == 1 && this.substring(0, 2)
            .toInt() >= 20) || (this.substring(5, 7).toInt() == 2 && this.substring(0, 2)
            .toInt() <= 18) -> 8
        (this.substring(5, 7).toInt() == 2 && this.substring(0, 2)
            .toInt() >= 19) || (this.substring(5, 7).toInt() == 3 && this.substring(0, 2)
            .toInt() <= 20) -> 3
        else -> 0
    }
}

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadImage.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

fun String.checkHoroscopeProperties(): UserHoroscopeProperties {
    return when (this) {
        "Aries" -> UserHoroscopeProperties(
            "Fire", "Red", "Tuesday"
        )
        "Taurus"
        -> UserHoroscopeProperties(
            "Earth", "Green", "Friday"
        )
        "Gemini"
        -> UserHoroscopeProperties(
            "Air", "Yellow", "Wednesday"
        )
        "Cancer"
        -> UserHoroscopeProperties(
            "Water", "White - Silver", "Monday"
        )
        "Leo"
        -> UserHoroscopeProperties(
            "Fire", "Gold", "Sunday"
        )
        "Virgo"
        -> UserHoroscopeProperties(
            "Earth", "Green - Brown", "Wednesday"
        )
        "Libra"
        -> UserHoroscopeProperties(
            "Air", "Pink - Blue", "Friday"
        )
        "Scorpio"
        -> UserHoroscopeProperties(
            "Water", "Black", "Tuesday"
        )
        "Sagittarius"
        -> UserHoroscopeProperties(
            "Fire", "Purple", "Thursday"
        )
        "Capricorn"
        -> UserHoroscopeProperties(
            "Earth", "Brown - Gray", "Saturday"
        )
        "Aquarius"
        -> UserHoroscopeProperties(
            "Air", "Blue", "Saturday"
        )
        "Pisces"
        -> UserHoroscopeProperties(
            "Water", "Light Green", "Thursday"
        )
        else -> throw Exception("")
    }
}

val horoscopeList = mutableListOf(
    Horoscope(1, "Aries", "", ""),
    Horoscope(2, "Taurus", "", ""),
    Horoscope(3, "Gemini", "", ""),
    Horoscope(4, "Cancer", "", ""),
    Horoscope(5, "Leo", "", ""),
    Horoscope(6, "Virgo", "", ""),
    Horoscope(7, "Libra", "", ""),
    Horoscope(8, "Scorpio", "", ""),
    Horoscope(9, "Sagittarius", "", ""),
    Horoscope(10, "Capricorn", "", ""),
    Horoscope(11, "Aquarius", "", ""),
    Horoscope(12, "Pisces", "", "")
)

fun String.checkRisingHoroscope(horoscope: Horoscope): Horoscope {
    return when {
        this.substring(0, 2) == "05" || this.substring(0, 2) == "06" -> horoscope
        this.substring(0, 2) == "07" || this.substring(0, 2) == "08" -> {
            if (horoscopeList.indexOf(horoscope) != horoscopeList.size - 1) horoscopeList[0]
            else horoscopeList[horoscopeList.indexOf(horoscope) + 1]
        }
        this.substring(0, 2) == "09" || this.substring(0, 2) == "10" -> {
            if (horoscopeList.indexOf(horoscope) != horoscopeList.size - 1) horoscopeList[1]
            else horoscopeList[horoscopeList.indexOf(horoscope) + 2]
        }
        this.substring(0, 2)
            .toInt() in 11..12 -> {
            if (horoscopeList.indexOf(horoscope) != horoscopeList.size - 1) horoscopeList[3]
            else horoscopeList[horoscopeList.indexOf(horoscope) + 3]
        }
        this.substring(0, 2)
            .toInt() in 13..14 -> {
            if (horoscopeList.indexOf(horoscope) != horoscopeList.size - 1) horoscopeList[4]
            else horoscopeList[horoscopeList.indexOf(horoscope) + 4]
        }
        this.substring(0, 2)
            .toInt() in 15..16 -> {
            if (horoscopeList.indexOf(horoscope) != horoscopeList.size - 1) horoscopeList[5]
            else horoscopeList[horoscopeList.indexOf(horoscope) + 5]
        }
        this.substring(0, 2)
            .toInt() in 17..18 -> {
            if (horoscopeList.indexOf(horoscope) != horoscopeList.size - 1) horoscopeList[6]
            else horoscopeList[horoscopeList.indexOf(horoscope) + 6]
        }
        this.substring(0, 2)
            .toInt() in 19..20 -> {
            if (horoscopeList.indexOf(horoscope) != horoscopeList.size - 1) horoscopeList[7]
            else horoscopeList[horoscopeList.indexOf(horoscope) + 7]
        }
        this.substring(0, 2)
            .toInt() in 21..22 -> {
            if (horoscopeList.indexOf(horoscope) != horoscopeList.size - 1) horoscopeList[8]
            else horoscopeList[horoscopeList.indexOf(horoscope) + 8]
        }
        this.substring(0, 2).toInt() == 23 || this.substring(0, 2) == "00" -> {
            if (horoscopeList.indexOf(horoscope) != horoscopeList.size - 1) horoscopeList[9]
            else horoscopeList[horoscopeList.indexOf(horoscope) + 9]
        }
        this.substring(0, 2) == "01" || this.substring(0, 2) == "02" -> {
            if (horoscopeList.indexOf(horoscope) != horoscopeList.size - 1) horoscopeList[10]
            else horoscopeList[horoscopeList.indexOf(horoscope) + 10]
        }
        this.substring(0, 2) == "03" || this.substring(0, 2) == "04" -> {
            if (horoscopeList.indexOf(horoscope) != horoscopeList.size - 1) horoscopeList[11]
            else horoscopeList[horoscopeList.indexOf(horoscope) + 11]
        }
        else -> throw Exception("")
    }
}

fun View.expandAnimation() {
    val anim = ScaleAnimation(
        0.0f, 1.0f,
        0.0f, 1.0f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    )
    anim.duration = 500
    startAnimation(anim)
}