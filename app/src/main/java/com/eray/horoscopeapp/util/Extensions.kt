package com.eray.horoscopeapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.eray.horoscopeapp.R

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
    return when {
        (this.substring(5, 7).toInt() == 3 && this.substring(0, 2)
            .toInt() >= 21) || (this.substring(5, 7).toInt() == 4 && this.substring(0, 2)
            .toInt() <= 19) -> "Aries" to R.drawable.ic_aries
        (this.substring(5, 7).toInt() == 4 && this.substring(0, 2)
            .toInt() >= 20) || (this.substring(5, 7).toInt() == 5 && this.substring(0, 2)
            .toInt() <= 20) -> "Taurus" to R.drawable.ic_taurus
        (this.substring(5, 7).toInt() == 5 && this.substring(0, 2)
            .toInt() >= 21) || (this.substring(5, 7).toInt() == 6 && this.substring(0, 2)
            .toInt() <= 20) -> "Gemini" to R.drawable.ic_gemini
        (this.substring(5, 7).toInt() == 6 && this.substring(0, 2)
            .toInt() >= 21) || (this.substring(5, 7).toInt() == 7 && this.substring(0, 2)
            .toInt() <= 22) -> "Cancer" to R.drawable.ic_cancer
        (this.substring(5, 7).toInt() == 7 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 8 && this.substring(0, 2)
            .toInt() <= 22) -> "Leo" to R.drawable.ic_leo
        (this.substring(5, 7).toInt() == 8 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 9 && this.substring(0, 2)
            .toInt() <= 22) -> "Virgo" to R.drawable.ic_virgo
        (this.substring(5, 7).toInt() == 9 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 10 && this.substring(0, 2)
            .toInt() <= 22) -> "Libra" to R.drawable.ic_libra
        (this.substring(5, 7).toInt() == 10 && this.substring(0, 2)
            .toInt() >= 23) || (this.substring(5, 7).toInt() == 11 && this.substring(0, 2)
            .toInt() <= 21) -> "Scorpio" to R.drawable.ic_scorpio
        (this.substring(5, 7).toInt() == 11 && this.substring(0, 2)
            .toInt() >= 22) || (this.substring(5, 7).toInt() == 12 && this.substring(0, 2)
            .toInt() <= 21) -> "Sagittarius" to R.drawable.ic_sagittarius
        (this.substring(5, 7).toInt() == 12 && this.substring(0, 2)
            .toInt() >= 22) || (this.substring(5, 7).toInt() == 1 && this.substring(0, 2)
            .toInt() <= 19) -> "Capricorn" to R.drawable.ic_capricorn
        (this.substring(5, 7).toInt() == 1 && this.substring(0, 2)
            .toInt() >= 20) || (this.substring(5, 7).toInt() == 2 && this.substring(0, 2)
            .toInt() <= 18) -> "Aquarius" to R.drawable.ic_aquarius
        (this.substring(5, 7).toInt() == 2 && this.substring(0, 2)
            .toInt() >= 19) || (this.substring(5, 7).toInt() == 3 && this.substring(0, 2)
            .toInt() <= 20) -> "Pisces" to R.drawable.ic_pisces
        else -> Pair("", 0)
    }
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

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadImage.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .placeholder(R.drawable.ic_info_white)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}