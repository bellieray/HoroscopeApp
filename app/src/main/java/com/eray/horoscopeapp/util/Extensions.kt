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